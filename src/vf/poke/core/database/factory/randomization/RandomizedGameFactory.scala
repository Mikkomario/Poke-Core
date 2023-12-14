package vf.poke.core.database.factory.randomization

import utopia.vault.nosql.factory.row.FromRowFactory
import utopia.vault.nosql.factory.row.linked.CombiningFactory
import utopia.vault.nosql.factory.row.model.FromRowModelFactory
import vf.poke.core.database.factory.game.GameFactory
import vf.poke.core.model.combined.randomization.RandomizedGame
import vf.poke.core.model.stored.game.Game
import vf.poke.core.model.stored.randomization.Randomization

/**
 * Used for pulling randomized game data (joined model)
 * @author Mikko Hilpinen
 * @since 2.12.2023, v1.0
 */
object RandomizedGameFactory extends CombiningFactory[RandomizedGame, Game, Randomization]
{
	override def parentFactory: FromRowModelFactory[Game] = GameFactory
	override def childFactory: FromRowFactory[Randomization] = RandomizationFactory
	
	override def apply(parent: Game, child: Randomization): RandomizedGame = RandomizedGame(parent.name, child)
}
