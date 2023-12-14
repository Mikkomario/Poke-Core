package vf.poke.companion.controller.app

import utopia.flow.util.NotEmpty
import utopia.flow.util.console.{ArgumentSchema, Command}
import vf.poke.companion.database.access.many.gameplay.run.DbDetailedRuns
import vf.poke.companion.database.model.gameplay.RunModel
import vf.poke.companion.model.combined.gameplay.DetailedRun
import vf.poke.companion.model.partial.gameplay.RunData
import vf.poke.core.database.access.many.game.DbGames
import vf.poke.core.database.access.many.randomization.DbRandomizations
import vf.poke.core.model.combined.randomization.RandomizedGame
import vf.poke.core.util.Common._

/**
 * Manages commands related to starting or continuing game runs
 * @author Mikko Hilpinen
 * @since 2.12.2023, v1.0
 */
class RunCommands(implicit env: PokeRunEnvironment)
{
	// ATTRIBUTES   ----------------------
	
	lazy val startCommand = Command("start", help = "Starts a new run")(
		ArgumentSchema("name", help = "Name to assign to this run"),
		ArgumentSchema("game", help = "Part of the name of the played game"))
	{ args =>
		cPool { implicit c =>
			// Identifies the targeted game / randomization
			val randomization = args("game").string match {
				case Some(gameName) =>
					val gameNames = DbGames.withNamesContaining(gameName).toMap
					if (gameNames.isEmpty) {
						println(s"No game matches \"$gameName\"")
						None
					}
					else
						DbRandomizations.forGames(gameNames.keys).takeLatest(1).headOption.map { randomization =>
							RandomizedGame(gameNames(randomization.gameId), randomization)
						}
				case None =>
					DbRandomizations.takeLatest(1).headOption.map { randomization =>
						val gameName = randomization.gameAccess.name
						RandomizedGame(gameName, randomization)
					}
			}
			randomization match {
				case Some(game) =>
					// Starts the run
					val run = RunModel.insert(RunData(args("name").stringOr("unnamed"), game.randomization.id))
					env.run = DetailedRun(run, game)
				case None => println("No randomization could be targeted. Can't start a new run at this time.")
			}
		}
	}
	lazy val continueCommand = Command("continue", help = "Continues a previously started run")(
		ArgumentSchema("run", "name", help = "Name of the run to continue")) { args =>
		cPool { implicit c =>
			args("run").string match {
				case Some(runName) =>
					NotEmpty(DbDetailedRuns.withName(runName).pull)
						.getOrElse { DbDetailedRuns.withGameNameContaining(runName).pull }
						.maxByOption { _.created } match
					{
						case Some(run) => env.run = run
						case None => println(s"No run matches '$runName'")
					}
				case None => println("Missing the run name")
			}
		}
	}
	
	
	// COMPUTED ------------------------
	
	def values = Vector(startCommand, continueCommand)
}
