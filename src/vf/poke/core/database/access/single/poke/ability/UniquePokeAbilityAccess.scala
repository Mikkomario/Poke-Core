package vf.poke.core.database.access.single.poke.ability

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.PokeAbilityFactory
import vf.poke.core.model.stored.poke.PokeAbility

object UniquePokeAbilityAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniquePokeAbilityAccess = new _UniquePokeAbilityAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniquePokeAbilityAccess(condition: Condition) extends UniquePokeAbilityAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct poke abilities.
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniquePokeAbilityAccess 
	extends UniquePokeAbilityAccessLike[PokeAbility] with SingleRowModelAccess[PokeAbility]
		with FilterableView[UniquePokeAbilityAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = PokeAbilityFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniquePokeAbilityAccess = 
		new UniquePokeAbilityAccess._UniquePokeAbilityAccess(mergeCondition(filterCondition))
}

