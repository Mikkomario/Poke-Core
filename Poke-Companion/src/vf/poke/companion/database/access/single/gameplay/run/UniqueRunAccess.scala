package vf.poke.companion.database.access.single.gameplay.run

import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.sql.Condition
import vf.poke.companion.database.factory.gameplay.RunFactory
import vf.poke.companion.model.stored.gameplay.Run

object UniqueRunAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueRunAccess = new _UniqueRunAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueRunAccess(condition: Condition) extends UniqueRunAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct runs.
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
trait UniqueRunAccess extends UniqueRunAccessLike[Run] with SingleChronoRowModelAccess[Run, UniqueRunAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = RunFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueRunAccess = 
		new UniqueRunAccess._UniqueRunAccess(mergeCondition(filterCondition))
}

