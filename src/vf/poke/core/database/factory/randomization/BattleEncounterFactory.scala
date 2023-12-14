package vf.poke.core.database.factory.randomization

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import vf.poke.core.database.PokeTables
import vf.poke.core.model.partial.randomization.BattleEncounterData
import vf.poke.core.model.stored.randomization.BattleEncounter

/**
  * Used for reading battle encounter data from the DB
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object BattleEncounterFactory extends FromValidatedRowModelFactory[BattleEncounter]
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def table = PokeTables.battleEncounter
	
	override protected def fromValidatedModel(valid: Model) = 
		BattleEncounter(valid("id").getInt, BattleEncounterData(valid("randomizationId").getInt, 
			valid("pokeId").getInt, valid("level").getInt, valid("numberOfEncounters").getInt))
}

