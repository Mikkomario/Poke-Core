package vf.poke.companion.controller.app

import utopia.flow.collection.CollectionExtensions._
import utopia.flow.parse.json.{JsonParser, JsonReader}
import utopia.flow.parse.file.FileExtensions._
import utopia.flow.time.TimeExtensions._
import utopia.flow.util.console.Console
import utopia.vault.database.columnlength.ColumnLengthRules
import vf.poke.companion.database.access.many.gameplay.run.DbRuns
import vf.poke.companion.database.model.gameplay.RunModel
import vf.poke.companion.model.combined.gameplay.DetailedRun
import vf.poke.companion.model.partial.gameplay.RunData
import vf.poke.core.database.access.single.game.DbGame
import vf.poke.core.database.access.single.randomization.DbRandomization
import vf.poke.core.util.Common._

import java.nio.file.Paths

/**
 * A command line companion application for randomized poke games
 * @author Mikko Hilpinen
 * @since 2.12.2023, v1.0
 */
object PokeCompanionApp extends App
{
	// ATTRIBUTES   -------------------
	
	private implicit val jsonParser: JsonParser = JsonReader
	
	private val lengthRulesDir = Paths.get("length-rules")
	
	
	// APP CODE -----------------------
	
	
	if (lengthRulesDir.notExists)
		println(s"WARNING: Length rules couldn't be found from ${lengthRulesDir.absolute}")
	
	lengthRulesDir.tryIterateChildrenCatching { _.map(ColumnLengthRules.loadFrom).toTryCatch }.anyFailure
		.foreach { e =>
			e.printStackTrace()
			println("Encountered an error during length-rule processing\n")
		}
	
	// Loads or starts a run
	cPool { implicit c =>
		DbRuns.detailed.takeLatest(1).headOption.orElse {
			DbRandomization.factory.latest.map { randomization =>
				println(s"Starting a new run on ${ randomization.gameAccess.name } randomization #${
					randomization.id} (from ${randomization.created.toLocalDateTime})")
				val run = RunModel.insert(RunData("First", randomization.id))
				val gameName = DbGame(randomization.gameId).name
				DetailedRun(run, randomization, gameName)
			}
		}
	} match {
		case Some(run) =>
			println(s"Continuing run ${run.name} of ${run.gameName}")
			implicit val env: PokeRunEnvironment = new PokeRunEnvironment(run)
			implicit val jsonParser: JsonParser = JsonReader
			val commands = new RunCommands().values ++ new TrainingCommands().values ++ new BattleCommands().values
			Console.static(commands, "\nNext command, please", "exit").run()
			
		case None =>
			println("No randomizations available yet. Please perform one randomization and try then again.")
			System.exit(0)
	}
}
