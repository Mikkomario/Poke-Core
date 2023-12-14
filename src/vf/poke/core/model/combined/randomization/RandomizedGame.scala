package vf.poke.core.model.combined.randomization

import vf.poke.core.model.stored.randomization.Randomization

/**
 * Represents a randomized game version. Includes game name.
 * @author Mikko Hilpinen
 * @since 2.12.2023, v1.0
 */
case class RandomizedGame(name: String, randomization: Randomization)
{
	def id = randomization.gameId
}
