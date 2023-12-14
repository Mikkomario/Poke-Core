package vf.poke.core.database.factory.game

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import vf.poke.core.database.PokeTables
import vf.poke.core.model.partial.game.GameData
import vf.poke.core.model.stored.game.Game

/**
  * Used for reading game data from the DB
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object GameFactory extends FromValidatedRowModelFactory[Game]
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def table = PokeTables.game
	
	override protected def fromValidatedModel(valid: Model) = 
		Game(valid("id").getInt, GameData(valid("name").getString))
}

