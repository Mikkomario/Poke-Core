package vf.poke.companion.model.combined.gameplay

import utopia.flow.view.template.Extender
import vf.poke.companion.model.partial.gameplay.RunData
import vf.poke.companion.model.stored.gameplay.Run
import vf.poke.core.model.combined.randomization.RandomizedGame
import vf.poke.core.model.stored.randomization.Randomization

object DetailedRun
{
	def apply(run: Run, game: RandomizedGame): DetailedRun = apply(run, game.randomization, game.name)
}

/**
 * Combines game, randomization and run information
 * @author Mikko Hilpinen
 * @since 2.12.2023, v1.0
 */
case class DetailedRun(run: Run, randomization: Randomization, gameName: String) extends Extender[RunData]
{
	// COMPUTED ------------------------
	
	def id = run.id
	
	
	// IMPLEMENTED  --------------------
	
	override def wrapped: RunData = run.data
}
