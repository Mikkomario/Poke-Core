package vf.poke.core.database.factory.randomization

import utopia.flow.collection.immutable.range.NumericSpan
import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import vf.poke.core.database.PokeTables
import vf.poke.core.model.partial.randomization.WildEncounterData
import vf.poke.core.model.stored.randomization.WildEncounter

/**
  * Used for reading wild encounter data from the DB
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object WildEncounterFactory extends FromValidatedRowModelFactory[WildEncounter]
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def table = PokeTables.wildEncounter
	
	override protected def fromValidatedModel(valid: Model) = 
		WildEncounter(valid("id").getInt, WildEncounterData(valid("randomizationId").getInt, 
			valid("zoneIndex").getInt, valid("pokeId").getInt, NumericSpan(valid("minLevel").getInt, 
			valid("maxLevel").getInt), valid("numberOfEncounters").getInt))
}

