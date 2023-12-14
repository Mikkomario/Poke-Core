package vf.poke.core.database.access.many.randomization.starter_assignment

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple starter assignments at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbStarterAssignments extends ManyStarterAssignmentsAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted starter assignments
	  * @return An access point to starter assignments with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbStarterAssignmentsSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbStarterAssignmentsSubset(targetIds: Set[Int]) extends ManyStarterAssignmentsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

