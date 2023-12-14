package vf.poke.companion.controller.app

import utopia.flow.collection.CollectionExtensions._
import utopia.flow.operator.equality.EqualsExtensions._
import utopia.flow.util.console.Command
import utopia.vault.database.Connection
import vf.poke.companion.database.access.many.gameplay.attack_experiment.DbAttackExperiments
import vf.poke.companion.database.access.single.gameplay.attack_experiment.DbAttackExperiment
import vf.poke.companion.database.access.single.gameplay.poke_capture.DbPokeCapture
import vf.poke.companion.database.access.single.gameplay.type_guess.DbTypeGuess
import vf.poke.companion.database.model.gameplay.{AttackExperimentModel, TypeGuessModel}
import vf.poke.companion.model.partial.gameplay.{AttackExperimentData, TypeGuessData}
import vf.poke.companion.model.stored.gameplay.TypeGuess
import vf.poke.core.model.cached.TypeSet
import vf.poke.core.model.enumeration.PokeType
import vf.poke.core.model.stored.poke.Poke
import vf.poke.core.util.Common._

/**
 * Commands that are useful when battling
 * @author Mikko Hilpinen
 * @since 12.12.2023, v1.0-alt
 */
class BattleCommands(implicit env: PokeRunEnvironment)
{
	// ATTRIBUTES   ----------------------
	
	lazy val registerAttackCommand = Command("attack", "att",
		help = "Registers an (experimental) attack against an opposing poke")(env.pokeArg, env.typeArg) { implicit args =>
		cPool { implicit c =>
			env.poke.foreach { poke =>
				env.selectedType.foreach { attackType =>
					if (registerAttack(attackType, poke))
						println("New information registered!\n")
					describeTypeOf(poke)
				}
			}
		}
	}
	lazy val guessTypeCommand = Command("guess", "g", help = "Guesses a type of a poke")(
		env.pokeArg, env.typeArg) { implicit args =>
		cPool { implicit c =>
			env.poke.foreach { poke =>
				lazy val previousGuess = DbTypeGuess.forPoke(poke.id).pull
				// Case: Clearing a previous guess
				if (args("type").getString ~== "None") {
					previousGuess match {
						case Some(previousGuess) =>
							previousGuess.access.deprecate()
							println(s"${poke.name}'s type is no longer assumed to be ${previousGuess.guessedType}")
						case None => println(s"There was no guess to clear")
					}
					describeTypeOf(poke)
				}
				// Case: Making a new guess
				else
					env.selectedType.foreach { guessedType =>
						val possibleTypes = possibleTypesOf(poke)
						// Case: Poke type is already known => Won't apply guessing
						if (possibleTypes hasSize 1)
							println(s"It is already known that the type of ${poke.name} is ${possibleTypes.head}")
						// Case: Guess is possible => Applies it
						else if (possibleTypes.exists { _.contains(guessedType) }) {
							if (previousGuess.forall { _.guessedType != guessedType }) {
								// Overwrites the previous guess, if appropriate
								previousGuess.foreach { _.access.deprecate() }
								TypeGuessModel.insert(TypeGuessData(env.run.id, poke.id, guessedType))
							}
							println(s"Guessed ${poke.name}'s type to be $guessedType")
							describeTypeOf(poke)
						}
						// Case: Guess is impossible => Rejects it
						else {
							println(s"It is already known that ${poke.name} is NOT of type $guessedType.")
							println(s"Valid guesses would be:")
							PokeType.values.filter { t => possibleTypes.exists { _.contains(t) } }.foreach { t =>
								println(s"\t- $t")
							}
						}
					}
			}
		}
	}
	lazy val describeTypeOfCommand = Command("type", "t",
		help = "Describes type information concerning the specified poke")(env.pokeArg) { implicit args =>
		cPool { implicit c => env.poke.foreach { describeTypeOf(_) } }
	}
	
	
	// COMPUTED --------------------------
	
	def values = Vector(registerAttackCommand, guessTypeCommand, describeTypeOfCommand)
	
	
	// OTHER    --------------------------
	
	private def describeTypeOf(poke: Poke)(implicit connection: Connection) = {
		val possibleTypes = possibleTypesOf(poke)
		val typeGuess = DbTypeGuess.forPoke(poke.id).pull
		// Case: Type is fully known based on the experiments
		if (possibleTypes hasSize 1) {
			// If the type is known for certain, no guessing is needed anymore
			typeGuess.foreach { _.access.deprecate() }
			describeKnownTypeOf(poke)
		}
		// Case: Type is only partially known
		else {
			// Applies the type guess to limit the options
			val guessLimitedTypes = typeGuess match {
				case Some(guess) =>
					val remainingTypes = possibleTypes.filter { _.contains(guess.guessedType) }
					if (remainingTypes.isEmpty) {
						guess.access.deprecate()
						possibleTypes
					}
					else {
						println(s"The following results are based on the guess that ${poke.name} is of type ${guess.guessedType}")
						remainingTypes
					}
				case None => possibleTypes
			}
			
			// Prints possible options (if limited enough)
			println(s"${poke.name} has ${guessLimitedTypes.size} possible types")
			if (guessLimitedTypes.hasSize <= 6)
				guessLimitedTypes.foreach { t => println(s"\t- $t") }
			else {
				// May list known partial type
				PokeType.values.find { t => guessLimitedTypes.forall { _.contains(t) } }.foreach { partialType =>
					println(s"${poke.name} is known to be of type $partialType, but secondary type is uncertain")
				}
			}
			
			val discernedEffectivenessMap = PokeType.values.flatMap { attackType =>
				val eff1 = visualEffectivenessOf(attackType.effectivenessAgainst(guessLimitedTypes.head))
				if (guessLimitedTypes.forall { t => visualEffectivenessOf(attackType.effectivenessAgainst(t)) == eff1 })
					Some(eff1 -> attackType)
				else
					None
			}.asMultiMap
			Vector(2.0 -> "weak", 0.5 -> "strong", 0.0 -> "immune").foreach { case (eff, word) =>
				discernedEffectivenessMap.get(eff).foreach { applicableTypes =>
					println(s"${poke.name} is $word against at least ${applicableTypes.size} types:")
					applicableTypes.foreach { t => println(s"\t- $t") }
				}
			}
		}
	}
	private def describeKnownTypeOf(poke: Poke) = {
		println(s"${poke.name} is of type ${poke.types}")
		println(s"Defensively, ${poke.name} is:")
		val attackEffectiveness = PokeType.values.map { t => t.effectivenessAgainst(poke.types) -> t }.asMultiMap
		Vector(4.0 -> "Very weak against", 2.0 -> "Weak against", 0.0 -> "Immune against",
			0.25 -> "Very strong against", 0.5 -> "Strong against")
			.foreach { case (eff, description) =>
				attackEffectiveness.get(eff).foreach { types =>
					println(s"\t- $description ${types.size} types: ${types.mkString(", ")}")
				}
			}
		
		println(s"Offensively, ${poke.name} is:")
		val offensiveEffectiveness = PokeType.values
			.map { defending => poke.types.types.map { _.effectivenessAgainst(defending) }.max -> defending }
			.asMultiMap
		Vector(2.0 -> "Strong against", 0.0 -> "Inneffective against", 0.5 -> "Weak against")
			.foreach { case (eff, description) =>
				offensiveEffectiveness.get(eff).foreach { types =>
					println(s"\t- $description ${types.size} types: ${types.mkString(", ")}")
				}
			}
		
		val partyTypeEffectiveness = env.party.map { p =>
			val eff = p.types.effectiveness
			val defense = eff.defenseRatingAgainst(poke.types)
			val offense = eff.offenseRatingAgainst(poke.types)
			(p, defense, defense + offense)
		}
		val bestAgainst = partyTypeEffectiveness.filter { _._3 > 0 }.sortBy { _._3 }.take(3)
		val defensivelyWeakAgainst = partyTypeEffectiveness.filter { _._2 < 0 }.reverseSortBy { _._2 }
		if (bestAgainst.nonEmpty) {
			println(s"The following pokes should be effective against ${poke.name}:")
			bestAgainst.foreach { case (poke, _, _) => println(s"\t- ${poke.name}") }
		}
		if (defensivelyWeakAgainst.nonEmpty) {
			println(s"The following pokes are in danger of super-effective hits:")
			defensivelyWeakAgainst.foreach { case (poke, _, _) => println(s"\t- ${poke.name}") }
		}
	}
	private def possibleTypesOf(poke: Poke)(implicit connection: Connection) = {
		// Case: Poke has been caught => Type is known for certain
		if (DbPokeCapture.ofPoke(poke.id).nonEmpty)
			Vector(poke.types)
		// Case: Poke hasn't been caught => Type may be discerned based on attack information
		else {
			val usedTypes = DbAttackExperiments.against(poke.id).attackTypes
			// Filters down possible types based on the attack results
			PokeType.values
				.flatMap { primaryType =>
					TypeSet(primaryType) +:
						PokeType.values.filterNot { _ == primaryType }.map { TypeSet(primaryType, _) }
				}
				.filter { possibleType =>
					usedTypes.forall { attackType =>
						val experiencedEffect = attackType.effectivenessAgainst(poke.types)
						val effectOnThisType = attackType.effectivenessAgainst(possibleType)
						visualEffectivenessOf(experiencedEffect) == visualEffectivenessOf(effectOnThisType)
					}
				}
		}
	}
	
	private def hasAttackedWith(attackType: PokeType, opponentPokeId: Int)(implicit connection: Connection) =
		DbAttackExperiment(attackType, opponentPokeId).nonEmpty
	
	private def registerAttack(attackType: PokeType, opponentPoke: Poke)(implicit connection: Connection) = {
		if (!hasAttackedWith(attackType, opponentPoke.id)) {
			AttackExperimentModel.insert(AttackExperimentData(env.run.id, opponentPoke.id, attackType))
			// The guessed type may have become invalidated now
			DbTypeGuess.forPoke(opponentPoke.id).pull.foreach { testGuessedType(opponentPoke, _) }
			true
		}
		else
			false
	}
	
	private def testGuessedType(poke: Poke, typeGuess: TypeGuess)(implicit connection: Connection) = {
		// Counts the number of possible type options that include the guessed type
		val guessedType = typeGuess.guessedType
		val usedAttackTypes = DbAttackExperiments.against(poke.id).attackTypes
		val possibleValues = (TypeSet(guessedType) +: PokeType.values.map { TypeSet(guessedType, _) })
			.filter { potentialType =>
				usedAttackTypes.forall { attackType =>
					// In order to be included, the type effectiveness must appear similar to what was experienced
					visualEffectivenessOf(attackType.effectivenessAgainst(potentialType)) ==
						visualEffectivenessOf(attackType.effectivenessAgainst(poke.types))
				}
			}
		// Case: This type is impossible => Invalidates the type guess
		if (possibleValues.isEmpty) {
			typeGuess.access.deprecate()
			println(s"${poke.name} is definitely NOT of type $guessedType. Removes that type guess.")
		}
	}
	
	private def visualEffectivenessOf(typeEffectiveness: Double) = {
		if (typeEffectiveness == 0.0 || typeEffectiveness == 1.0)
			typeEffectiveness
		else if (typeEffectiveness > 1.0)
			2.0
		else
			0.5
	}
}
