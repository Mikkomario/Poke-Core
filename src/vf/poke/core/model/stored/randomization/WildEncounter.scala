package vf.poke.core.model.stored.randomization

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.core.database.access.single.randomization.wild_encounter.DbSingleWildEncounter
import vf.poke.core.model.partial.randomization.WildEncounterData

/**
  * Represents a wild encounter that has already been stored in the database
  * @param id id of this wild encounter in the database
  * @param data Wrapped wild encounter data
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class WildEncounter(id: Int, data: WildEncounterData) extends StoredModelConvertible[WildEncounterData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this wild encounter in the database
	  */
	def access = DbSingleWildEncounter(id)
}

