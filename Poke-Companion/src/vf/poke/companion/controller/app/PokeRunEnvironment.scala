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
							val statMap = stats.toMap
							val bst = stats.map { _._2 }.sum
							val defensiveValue = (statMap.getOrElse(Defense, 0) +
								statMap.getOrElse(SpecialDefense, 0)) / 2.0 * statMap.getOrElse(Hp, 0)
							val offensiveValue = (statMap.get(Attack).toVector ++ statMap.get(SpecialAttack))
								.maxOption.getOrElse(0) * math.pow(statMap.getOrElse(Speed, 0).toDouble, 0.7)
							(bst, offensiveValue, defensiveValue)
						}
						.toVector
					// Creates sorted collections based on the power levels
					val indexSpread = powerIndexThresholds.map { r => (r * powerLevels.size).toInt } +
						powerDescriptions
					val orderedLists = Vector(
						"BST" -> powerLevels.sortBy { _._2._1 },
						"offense" -> powerLevels.sortBy { _._2._2 },
						"defense" -> powerLevels.sortBy { _._2._3 }
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
	}
	
	
	// COMPUTED --------------------------
	
	def run = runPointer.value
	def run_=(newRun: DetailedRun) = runPointer.value = newRun
	
	def randomizationId = run.randomizationId
	
	def party = partyPointer.value
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
	
	private def lazyWithConnection[A](f: Connection => A) =
		ConditionalLazy { cPool.tryWith(f) } { _.isSuccess }
}
