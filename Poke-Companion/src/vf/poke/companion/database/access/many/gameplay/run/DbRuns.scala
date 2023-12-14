package vf.poke.companion.database.access.many.gameplay.run

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple runs at a time
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object DbRuns extends ManyRunsAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted runs
	  * @return An access point to runs with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbRunsSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbRunsSubset(targetIds: Set[Int]) extends ManyRunsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

