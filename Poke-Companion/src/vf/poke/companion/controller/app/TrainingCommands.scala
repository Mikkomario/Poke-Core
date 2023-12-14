package vf.poke.companion.controller.app

import utopia.flow.collection.immutable.range.NumericSpan
import utopia.flow.util.console.{ArgumentSchema, Command}
import utopia.vault.database.Connection
import vf.poke.companion.database.access.many.gameplay.attack_experiment.DbAttackExperiments
import vf.poke.companion.database.access.single.gameplay.poke_capture.DbPokeCapture
import vf.poke.companion.database.access.single.gameplay.poke_training.DbPokeTraining
import vf.poke.companion.database.model.gameplay.{PokeCaptureModel, PokeTrainingModel}
import vf.poke.companion.model.partial.gameplay.{PokeCaptureData, PokeTrainingData}
import vf.poke.core.database.access.many.poke.DbPokes
import vf.poke.core.database.access.many.poke.ability.DbDetailedPokeAbilities
import vf.poke.core.database.access.many.poke.evo.DbEvos
import vf.poke.core.database.access.many.poke.stat.DbPokeStats
import vf.poke.core.database.access.many.randomization.move.DbLearntMoves
import vf.poke.core.database.access.many.randomization.starter_set.DbDetailedStarterSets
import vf.poke.core.model.cached.{SpreadThresholds, SpreadValues}
import vf.poke.core.model.enumeration.Stat.{Attack, Defense, SpecialAttack, SpecialDefense}
import vf.poke.core.model.stored.poke.Poke
import vf.poke.core.util.Common._

object TrainingCommands
{
	private val physicalToSpecialSpread = SpreadThresholds(0.7, 1.0, 1.4) +
		SpreadValues("special", "mixed (special)", "mixed (physical)", "physical")
}

/**
 * Interface for constructing and accessing commands that help in poke-training
 * @author Mikko Hilpinen
 * @since 12.12.2023, v1.0-alt
 */
class TrainingCommands(implicit env: PokeRunEnvironment)
{
	import TrainingCommands._
	
	// ATTRIBUTES   ---------------------
	
	lazy val levelCommand = Command("lvl",
		help = "Records the fact that you reached the specified level in poke training, or captured a poke")(
		env.pokeArg, ArgumentSchema("level", "lvl", help = "The level reached with the specified poke")) { implicit args =>
		cPool { implicit c =>
			env.poke.foreach { poke =>
				args("level").int match {
					case Some(level) => captureOrLevel(poke, level)
					case None => println(s"Level not specified!")
				}
			}
		}
	}
	lazy val potentialCommand = Command("power", "pw", help = "Describes the power level of a poke")(
		env.pokeArg) { implicit args =>
		cPool { implicit c =>
			env.poke.foreach { poke =>
				// Checks whether power-estimate is allowed
				// For this, the poke must be captured first
				// Alternatively, if there are enough attack experiments, this feature will be available as well
				if (isCaptured(poke.id) || attackExperimentCountAgainst(poke.id) >= 4) {
					println(s"Attributes of ${poke.name}:")
					env.powerLevelsOf(poke.id).foreach { case (power, level) =>
						println(s"\t- ${power.capitalize}: $level")
					}
					// Also reads physical to special ratios
					val stats = DbPokeStats.ofPoke(poke.id).toMap.withDefaultValue(1)
					val defToSpDef = stats(Defense) / stats(SpecialDefense).toDouble
					val attToSpAtt = stats(Attack) / stats(SpecialAttack).toDouble
					Vector(
						"Defensive bias" -> defToSpDef,
						"Offensive bias" -> attToSpAtt
					).foreach { case (description, value) =>
						println(s"\t- $description: ${physicalToSpecialSpread(value)}")
					}
				}
				else
					println("You are not familiar enough with this poke yet")
			}
		}
	}
	lazy val abilitiesCommand = Command("abilities", "abil", help = "Lists the standard abilities of a poke")(
		env.pokeArg) { implicit args =>
		cPool { implicit c =>
			env.poke.foreach { poke =>
				// The poke must be captured or fought many times for this command to be enabled
				if (isCaptured(poke.id) || attackExperimentCountAgainst(poke.id) > 6) {
					val abilities = DbDetailedPokeAbilities.ofPoke(poke.id).normal.pull
					println(s"${poke.name} has ${abilities.size} normal abilities:")
					abilities.foreach { a => println(s"\t- ${a.ability.name}") }
				}
				else
					println("You are not familiar enough with this poke yet")
			}
		}
	}
	lazy val evoMoveChooseCommand = Command("evomoves", "moves",
		help = "Lists the choice of moves between the current and upcoming evo form")(env.pokeArg) { implicit args =>
		cPool { implicit c =>
			env.poke.foreach { poke =>
				// Makes sure the poke has been captured
				currentLevelOf(poke.id) match {
					case Some(currentLevel) =>
						// Checks the evo level
						val evos = DbEvos.from(poke.id).pull
						evos.find { _.levelThreshold.forall { _ <= currentLevel + 5 } } match {
							case Some(evo) =>
								val levelThreshold = evo.levelThreshold match {
									case Some(t) => t max (currentLevel + 1)
									case None => currentLevel
								}
								val moves = DbLearntMoves.byPokes(Set(poke.id, evo.toId))
									.duringLevels(NumericSpan(levelThreshold, levelThreshold + 5))
									.pull.groupBy { _.learn.pokeId }
									.view.mapValues { _.sortBy { _.learn.level } }.toMap.withDefaultValue(Vector())
								Vector(
									s"If ${poke.name} evolves" -> evo.toId,
									s"If ${poke.name} doesn't evolve" -> poke.id
								).foreach { case (description, pokeId) =>
									val movesInThisScenario = moves(pokeId)
									if (movesInThisScenario.isEmpty)
										println(s"$description, it won't learn any moves")
									else {
										println(s"$description, it will learn the following moves:")
										movesInThisScenario.foreach { move =>
											println(s"\t- Lvl ${move.learn.level}: ${move.name}")
										}
									}
								}
							case None =>
								if (evos.nonEmpty)
									println(s"${poke.name} is not evolving soon")
								else
									println(s"${poke.name} doesn't evolve")
						}
					case None => println("This command is only available for captured pokes")
				}
			}
		}
	}
	lazy val describeStartersCommand = Command.withoutArguments("starters",
		help = "Describes the starter pokes in a bit more detail") {
		cPool { implicit c =>
			// Reads the starters
			val starterSets = DbDetailedStarterSets.inRandomization(env.randomizationId).pull
			val pokeMap = DbPokes(starterSets.flatMap { _.pokeIds }.toSet).toMapBy { _.id }
			// Reads evolved forms
			val evoMap = fullyEvolved(pokeMap.values.toSet).groupMap { _._1 } { _._2 }
			val pokeIds = evoMap.flatMap { case (from, to) => to.map { _.id }.toSet + from.id }.toSet
			// Reads BST values
			val bstPerPokeId = DbPokeStats.ofPokes(pokeIds).pull.groupBy { _.pokeId }
				.view.mapValues { _.map { _.value }.sum }.toMap.withDefaultValue(0)
			// Reads abilities
			val abilitiesPerPokeId = DbDetailedPokeAbilities.normal.ofPokes(pokeIds).pull
				.groupBy { _.pokeId }.withDefaultValue(Vector())
			
			// Describes the starters
			starterSets.zipWithIndex.foreach { case (starters, index) =>
				println(s"Starter set ${index + 1}/${starterSets.size}:")
				starters.assignments.foreach { assignment =>
					val baseForm = pokeMap(assignment.pokeId)
					val finalForms = evoMap(baseForm)
					val baseBst = bstPerPokeId(baseForm.id)
					val evolvedBst = finalForms.map { p => bstPerPokeId(p.id) }.toVector.sorted
					val baseAbilities = abilitiesPerPokeId(baseForm.id)
					val additionalAbilities = finalForms.flatMap { p => abilitiesPerPokeId(p.id) }
						.filterNot { a => baseAbilities.exists { _.abilityId == a.abilityId } }
					
					println(s"\t- ${baseForm.name}")
					println(s"\t\t- Initial type is ${baseForm.types}, fully evolved is ${
						finalForms.map { _.types }.mkString(" / ") }")
					println(s"\t\t- BST starts with $baseBst and reaches ${ evolvedBst.mkString(" / ") }")
					println(s"\t\t- Has ${baseAbilities.size} possible abilities:")
					baseAbilities.foreach { a => println(s"\t\t\t- ${a.ability.name}") }
					if (additionalAbilities.nonEmpty) {
						println(s"\t\t- Evolved abilities differ:")
						additionalAbilities.foreach { a => println(s"\t\t\t- ${a.ability.name}") }
					}
				}
			}
		}
	}
	
	
	// COMPUTED -------------------------
	
	def values = Vector(levelCommand, potentialCommand, abilitiesCommand, evoMoveChooseCommand,
		describeStartersCommand)
	
	
	// OTHER    -------------------------
	
	private def isCaptured(pokeId: Int)(implicit connection: Connection) = DbPokeCapture.ofPoke(pokeId).nonEmpty
	private def attackExperimentCountAgainst(pokeId: Int)(implicit connection: Connection) =
		DbAttackExperiments.against(pokeId).size
	
	private def currentLevelOf(pokeId: Int)(implicit connection: Connection) =
		DbPokeTraining.ofPoke(pokeId).level.orElse { DbPokeCapture.ofPoke(pokeId).level }
	private def trainingLevelsFor(pokeId: Int)(implicit connection: Connection) =
		DbPokeCapture.ofPoke(pokeId).level.map { captureLevel =>
			val currentLevel = DbPokeTraining.ofPoke(pokeId).level.getOrElse(captureLevel)
			NumericSpan(captureLevel, currentLevel)
		}
	
	private def fullyEvolved(pokes: Iterable[Poke])(implicit connection: Connection): Iterable[(Poke, Poke)] = {
		val evos = DbEvos.from(pokes.map { _.id }).toMap
		if (evos.isEmpty)
			pokes.map { p => p -> p }
		else {
			val nextFormById = DbPokes(evos.valuesIterator.toSet).toMapBy { _.id }
			// Moves to the next set of pokes using recursion
			val nextEvolveMap = fullyEvolved(nextFormById.valuesIterator.toSet)
			// Maps each poke to their final evo form
			pokes.flatMap { p =>
				// Replaces the direct evo forms with final forms
				val evolved = evos.get(p.id).flatMap(nextFormById.get).toVector.flatMap { evolved =>
					val moreEvolved = nextEvolveMap.filter { _._1 == evolved }
					if (moreEvolved.nonEmpty)
						moreEvolved.map { case (_, fullyEvolved) => p -> fullyEvolved }
					else
						Some(p -> evolved)
				}
				if (evolved.nonEmpty)
					evolved
				else
					Some(p -> p)
			}
		}
	}
	
	private def captureOrLevel(poke: Poke, level: Int)(implicit connection: Connection) = {
		DbPokeTraining.ofPoke(poke.id).pull match {
			// Case: Already trained before => Updates the record
			case Some(existingTraining) =>
				if (existingTraining.level < level) {
					existingTraining.access.deprecate()
					PokeTrainingModel.insert(PokeTrainingData(env.run.id, poke.id, level))
					println(s"${poke.name} trained from ${existingTraining.level} to $level")
				}
				env.onParty(poke)
			// Case: Not trained before
			case None =>
				DbPokeCapture.ofPoke(poke.id).level match {
					// Case: Captured before => Records a training event
					case Some(captureLevel) =>
						if (level > captureLevel) {
							PokeTrainingModel.insert(PokeTrainingData(env.run.id, poke.id, level))
							println(s"${poke.name} trained from $captureLevel to $level")
						}
						env.onParty(poke)
					// Case: Not captured before => Records a capture
					case None =>
						PokeCaptureModel.insert(PokeCaptureData(env.run.id, poke.id, level))
						println(s"${poke.name} marked as captured!")
				}
		}
	}
}
