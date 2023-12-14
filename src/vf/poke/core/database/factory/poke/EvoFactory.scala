package vf.poke.core.database.factory.poke

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import vf.poke.core.database.PokeTables
import vf.poke.core.model.partial.poke.EvoData
import vf.poke.core.model.stored.poke.Evo

/**
  * Used for reading evo data from the DB
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object EvoFactory extends FromValidatedRowModelFactory[Evo]
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def table = PokeTables.evo
	
	override protected def fromValidatedModel(valid: Model) = 
		Evo(valid("id").getInt, EvoData(valid("fromId").getInt, valid("toId").getInt, 
			valid("levelThreshold").int, valid("itemId").int))
}

