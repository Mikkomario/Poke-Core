package vf.poke.core.database.factory.game

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import vf.poke.core.database.PokeTables
import vf.poke.core.model.partial.game.ItemData
import vf.poke.core.model.stored.game.Item

/**
  * Used for reading item data from the DB
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object ItemFactory extends FromValidatedRowModelFactory[Item]
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def table = PokeTables.item
	
	override protected def fromValidatedModel(valid: Model) = 
		Item(valid("id").getInt, ItemData(valid("gameId").getInt, valid("indexInGame").getInt, 
			valid("name").getString))
}

