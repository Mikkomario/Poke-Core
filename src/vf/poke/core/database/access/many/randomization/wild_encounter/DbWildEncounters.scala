package vf.poke.core.database.access.many.randomization.wild_encounter

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple wild encounters at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbWildEncounters extends ManyWildEncountersAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted wild encounters
	  * @return An access point to wild encounters with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbWildEncountersSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbWildEncountersSubset(targetIds: Set[Int]) extends ManyWildEncountersAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

