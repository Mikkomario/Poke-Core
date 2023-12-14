package vf.poke.core.database.access.many.randomization.battle_encounter

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple battle encounters at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbBattleEncounters extends ManyBattleEncountersAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted battle encounters
	  * @return An access point to battle encounters with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbBattleEncountersSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbBattleEncountersSubset(targetIds: Set[Int]) extends ManyBattleEncountersAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

