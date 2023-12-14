package vf.poke.core.database.factory.poke

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import vf.poke.core.database.PokeTables
import vf.poke.core.model.partial.poke.EvoMoveData
import vf.poke.core.model.stored.poke.EvoMove

/**
  * Used for reading evo move data from the DB
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object EvoMoveFactory extends FromValidatedRowModelFactory[EvoMove]
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def table = PokeTables.evoMove
	
	override protected def fromValidatedModel(valid: Model) = 
		EvoMove(valid("id").getInt, EvoMoveData(valid("pokeId").getInt, valid("moveId").getInt))
}

