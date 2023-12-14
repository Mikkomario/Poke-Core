package vf.poke.core.database.access.single.randomization.wild_encounter

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.stored.randomization.WildEncounter

/**
  * An access point to individual wild encounters, based on their id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DbSingleWildEncounter(id: Int) 
	extends UniqueWildEncounterAccess with SingleIntIdModelAccess[WildEncounter]

