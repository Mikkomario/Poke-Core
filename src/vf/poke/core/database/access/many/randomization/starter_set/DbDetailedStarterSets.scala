package vf.poke.core.database.access.many.randomization.starter_set

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple detailed starter sets at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbDetailedStarterSets extends ManyDetailedStarterSetsAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted detailed starter sets
	  * @return An access point to detailed starter sets with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbDetailedStarterSetsSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbDetailedStarterSetsSubset(targetIds: Set[Int]) extends ManyDetailedStarterSetsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

