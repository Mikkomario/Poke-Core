package vf.poke.core.database.access.single.randomization.battle_encounter

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.stored.randomization.BattleEncounter

/**
  * An access point to individual battle encounters, based on their id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DbSingleBattleEncounter(id: Int) 
	extends UniqueBattleEncounterAccess with SingleIntIdModelAccess[BattleEncounter]

