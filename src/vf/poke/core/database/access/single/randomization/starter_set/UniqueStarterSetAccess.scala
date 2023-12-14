package vf.poke.core.database.access.single.randomization.starter_set

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.StarterSetFactory
import vf.poke.core.model.stored.randomization.StarterSet

object UniqueStarterSetAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueStarterSetAccess = new _UniqueStarterSetAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueStarterSetAccess(condition: Condition) extends UniqueStarterSetAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct starter sets.
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniqueStarterSetAccess 
	extends UniqueStarterSetAccessLike[StarterSet] with SingleRowModelAccess[StarterSet] 
		with FilterableView[UniqueStarterSetAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = StarterSetFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueStarterSetAccess = 
		new UniqueStarterSetAccess._UniqueStarterSetAccess(mergeCondition(filterCondition))
}

