package vf.poke.core.database.access.single.randomization.starter_set

import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.DetailedStarterSetFactory
import vf.poke.core.database.model.randomization.StarterAssignmentModel
import vf.poke.core.model.combined.randomization.DetailedStarterSet

object UniqueDetailedStarterSetAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueDetailedStarterSetAccess = 
		new _UniqueDetailedStarterSetAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueDetailedStarterSetAccess(condition: Condition) extends UniqueDetailedStarterSetAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return distinct detailed starter sets
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniqueDetailedStarterSetAccess 
	extends UniqueStarterSetAccessLike[DetailedStarterSet] with FilterableView[UniqueDetailedStarterSetAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked assignments
	  */
	protected def assignmentModel = StarterAssignmentModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = DetailedStarterSetFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueDetailedStarterSetAccess = 
		new UniqueDetailedStarterSetAccess._UniqueDetailedStarterSetAccess(mergeCondition(filterCondition))
}

