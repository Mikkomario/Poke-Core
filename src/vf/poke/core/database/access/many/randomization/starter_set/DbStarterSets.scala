package vf.poke.core.database.access.many.randomization.starter_set

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple starter sets at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbStarterSets extends ManyStarterSetsAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted starter sets
	  * @return An access point to starter sets with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbStarterSetsSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbStarterSetsSubset(targetIds: Set[Int]) extends ManyStarterSetsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

