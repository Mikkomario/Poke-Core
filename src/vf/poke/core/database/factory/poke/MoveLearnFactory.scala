package vf.poke.core.database.factory.poke

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import vf.poke.core.database.PokeTables
import vf.poke.core.model.partial.poke.MoveLearnData
import vf.poke.core.model.stored.poke.MoveLearn

/**
  * Used for reading move learn data from the DB
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object MoveLearnFactory extends FromValidatedRowModelFactory[MoveLearn]
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def table = PokeTables.moveLearn
	
	override protected def fromValidatedModel(valid: Model) = 
		MoveLearn(valid("id").getInt, MoveLearnData(valid("pokeId").getInt, valid("moveId").getInt, 
			valid("level").getInt))
}

