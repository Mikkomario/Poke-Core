package vf.poke.core.database.access.single.poke

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.PokeFactory
import vf.poke.core.model.stored.poke.Poke

object UniquePokeAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniquePokeAccess = new _UniquePokeAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniquePokeAccess(condition: Condition) extends UniquePokeAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct pokes.
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniquePokeAccess 
	extends UniquePokeAccessLike[Poke] with SingleRowModelAccess[Poke] with FilterableView[UniquePokeAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = PokeFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniquePokeAccess = 
		new UniquePokeAccess._UniquePokeAccess(mergeCondition(filterCondition))
}

