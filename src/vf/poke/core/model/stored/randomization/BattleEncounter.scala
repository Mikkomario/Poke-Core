package vf.poke.core.model.stored.randomization

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.core.database.access.single.randomization.battle_encounter.DbSingleBattleEncounter
import vf.poke.core.model.partial.randomization.BattleEncounterData

/**
  * Represents a battle encounter that has already been stored in the database
  * @param id id of this battle encounter in the database
  * @param data Wrapped battle encounter data
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class BattleEncounter(id: Int, data: BattleEncounterData) 
	extends StoredModelConvertible[BattleEncounterData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this battle encounter in the database
	  */
	def access = DbSingleBattleEncounter(id)
}

