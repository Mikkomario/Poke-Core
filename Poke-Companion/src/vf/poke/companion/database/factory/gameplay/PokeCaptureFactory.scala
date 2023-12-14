package vf.poke.companion.database.factory.gameplay

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import vf.poke.companion.database.PokeCompanionTables
import vf.poke.companion.model.partial.gameplay.PokeCaptureData
import vf.poke.companion.model.stored.gameplay.PokeCapture

/**
  * Used for reading poke capture data from the DB
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object PokeCaptureFactory extends FromValidatedRowModelFactory[PokeCapture]
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def table = PokeCompanionTables.pokeCapture
	
	override protected def fromValidatedModel(valid: Model) = 
		PokeCapture(valid("id").getInt, PokeCaptureData(valid("runId").getInt, valid("pokeId").getInt, 
			valid("level").getInt, valid("created").getInstant))
}

