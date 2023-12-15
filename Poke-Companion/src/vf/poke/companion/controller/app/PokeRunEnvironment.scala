package vf.poke.companion.controller.app

import utopia.flow.collection.CollectionExtensions._
import utopia.flow.operator.equality.EqualsExtensions._
import utopia.flow.time.Now
import utopia.flow.time.TimeExtensions._
import utopia.flow.util.console.ConsoleExtensions._
import utopia.flow.util.console.{ArgumentSchema, CommandArguments}
import utopia.flow.view.immutable.caching.ConditionalLazy
import utopia.flow.view.mutable.eventful.EventfulPointer
import utopia.vault.database.Connection
import vf.poke.companion.database.access.many.gameplay.poke_training.DbPokeTrainings
import vf.poke.companion.model.combined.gameplay.DetailedRun
import vf.poke.core.database.access.many.poke.DbPokes
import vf.poke.core.database.access.many.poke.evo.DbEvos
import vf.poke.core.database.access.many.poke.stat.DbPokeStats
import vf.poke.core.database.access.many.randomization.starter_set.DbStarterSets
import vf.poke.core.database.access.many.randomization.wild_encounter.DbWildEncounters
import vf.poke.core.model.cached.{SpreadThresholds, SpreadValues}
import vf.poke.core.model.enumeration.PokeType
import vf.poke.core.model.enumeration.Stat._
import vf.poke.core.model.stored.poke.Poke
import vf.poke.core.util.Common._

import scala.io.StdIn
import scala.util.{Failure, Success}

object PokeRunEnvironment
{
	// Thresholds in terms of ranking. E.g. 0.8 means 80% means top 20%
	private val powerIndexThresholds = SpreadThresholds(0.3, 0.5, 0.8)
	private val powerDescriptions = SpreadValues("very weak", "weak", "strong", "very strong")
}

/**
 * Contains variables and constants used during app runtime
 * @author Mikko Hilpinen
 * @since 2.12.2023, v1.0
 */
class PokeRunEnvironment(initialRun: DetailedRun)
{
	import PokeRunEnvironment._
	
	// ATTRIBUTES   ----------------------
	
	val pokeArg = ArgumentSchema("poke", "p", help = "Name of the targeted poke")
	val typeArg = ArgumentSchema("type", "t", help = "A poke type")
	
	val runPointer = EventfulPointer(initialRun)
	val partyPointer = EventfulPointer(Vector[Poke]())
	
	// TODO: Create evo- and level based categories in which the comparison is made
	private val inGamePokeIdsPointer = runPointer.map { run =>
		lazyWithConnection { implicit c =>
			// Finds the pokes that appear as:
			//      1) Starters
			//      2) Wild encounters
			val starterIds = DbStarterSets.inRandomization(run.randomizationId).pokeIds
			val wildPokeIds = DbWildEncounters.inRandomization(run.randomizationId).pokeIds.toSet
			// Finds the evolved forms as well
			Iterator
				.iterate(starterIds ++ wildPokeIds) { lastPokeIds =>
					DbEvos.from(lastPokeIds).toIds.toSet -- lastPokeIds
				}
				.takeWhile { _.nonEmpty }.reduce { _ ++ _ }
		}
	}
	private val statPowerRangesPointer = inGamePokeIdsPointer.map { lazyPokeIds =>
		ConditionalLazy {
			lazyPokeIds.value.flatMap { pokeIds =>
				cPool.tryWith { implicit c =>
					// Collects power-related data based on poke stats
					// Only includes pokes that appear in the game
					val powerLevels = DbPokeStats.ofPokes(pokeIds).pull
						.groupMap { _.pokeId } { s => s.stat -> s.value }.view
						.mapValues { stats =>
							val statMap = stats.toMap.withDefaultValue(0)
							val bst = stats.map { _._2 }.sum
							val defensiveValue = (statMap(Defense) + statMap(SpecialDefense)) / 2.0 * statMap(Hp)
							val speed = statMap(Speed)
							val offensiveValue = (statMap(Attack) max statMap(SpecialAttack)) *
								math.pow(speed.toDouble, 0.5)
							
							(bst, offensiveValue, defensiveValue, speed)
						}
						.toVector
					// Creates sorted collections based on the power levels
					val indexSpread = powerIndexThresholds.map { r => (r * powerLevels.size).toInt } +
						powerDescriptions
					val orderedLists = Vector(
						"BST" -> powerLevels.sortBy { _._2._1 },
						"offense" -> powerLevels.sortBy { _._2._2 },
						"defense" -> powerLevels.sortBy { _._2._3 },
						"speed" -> powerLevels.sortBy { _._2._4 }
					).map { case (powerName, orderedList) => powerName -> orderedList.map { _._1 } }
					indexSpread -> orderedLists
				}
			}
		} { _.isSuccess }
	}
	
	
	// INITIAL CODE ----------------------
	
	// Prints whenever run changes
	runPointer.addContinuousListener { e =>
		val run = e.newValue
		println(s"Using run ${run.name} of ${run.gameName}, started ${run.created.toLocalDate} (${
			(Now - run.created).description } ago)")
		
		// Also updates the party
		loadParty()
	}
	
	// Prints when party changes
	partyPointer.addContinuousListener { e =>
		val (changes, _) = e.values.separateMatchingWith { _.id == _.id }
		val names = changes.map { _.map { _.name } }
		val removed = names.first
		val added = names.second
		
		if (removed.nonEmpty) {
			if (added.nonEmpty)
				println(s"Swaps ${removed.mkString(" and ")} to ${added.mkString(" and ")}")
			else
				println(s"Removes ${ removed.mkString(" and ") } from the party")
		}
		else if (added.nonEmpty)
			println(s"Adds ${ added.mkString(" and ") } to the party")
	}
	
	loadParty()
	
	
	// COMPUTED --------------------------
	
	def run = runPointer.value
	def run_=(newRun: DetailedRun) = runPointer.value = newRun
	
	def randomizationId = run.randomizationId
	
	def party = partyPointer.value
	def party_=(newParty: Vector[Poke]) = partyPointer.value = newParty
	def hasParty = party.nonEmpty
	
	def poke(implicit connection: Connection, args: CommandArguments) = {
		args("poke").string match {
			case Some(pokeName) =>
				val options = DbPokes.inRandomization(randomizationId).withNameLike(pokeName).pull.sortBy { _.name }
					.bestMatch { _.name ~== pokeName }
				if (options.nonEmpty) {
					val selected = options.oneOrMany match {
						case Left(unique) => Some(unique)
						case Right(options) =>
							println(s"Found ${options.size} options:")
							options.zipWithIndex.foreach { case (poke, index) =>
								println(s"\t- #${index + 1}: ${poke.name}")
							}
							println("Please select the correct one by typing the matching index")
							StdIn.read().int.flatMap { i => options.lift(i - 1) }
					}
					if (selected.isEmpty)
						println("No poke selected!")
					selected.filterNot { _.name ~== pokeName }.foreach { p => println(s"Selected ${p.name}") }
					selected
				}
				else {
					println(s"No poke matches name '$pokeName'")
					None
				}
			case None =>
				println("Missing required argument 'poke'")
				None
		}
	}
	def selectedType(implicit args: CommandArguments) = {
		val typeName = args("type").getString
		val t = PokeType.findForName(typeName)
		if (t.isEmpty)
			println(s"'$typeName' is not recognized as a type")
		t
	}
	
	
	// OTHER    -------------------------
	
	def parseType(typeString: String) = {
		val t = PokeType.findForName(typeString)
		if (t.isEmpty)
			println(s"'$typeString' is not recognized as a type")
		t
	}
	
	def powerLevelsOf(pokeId: Int) = statPowerRangesPointer.value.value match {
		case Success((spread, lists)) =>
			// Finds the poke's index from the lists (if possible) and uses that information to determine
			// the approximate power level
			lists.flatMap { case (name, list) => list.findIndexOf(pokeId).map { index => name -> spread(index) } }
		// Case: DB reading failed
		case Failure(error) =>
			error.printStackTrace()
			println("Can't access power levels at this time")
			Vector()
	}
	
	/**
	 * Records that a poke is in the current party
	 * @param poke A poke that's in the current party
	 */
	def onParty(poke: Poke) = partyPointer.update { poke +: _.filter { _ != poke }.take(5) }
	
	private def loadParty() = {
		cPool.tryWith { implicit c =>
			party = DbPokes(DbPokeTrainings.takeHighestLevels(6).map { _.pokeId }.toSet).pull
		}.failure.foreach { _.printStackTrace() }
	}
	
	private def lazyWithConnection[A](f: Connection => A) =
		ConditionalLazy { cPool.tryWith(f) } { _.isSuccess }
}
