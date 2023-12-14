package vf.poke.core.database.access.single.randomization.move

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.MoveFactory
import vf.poke.core.database.model.randomization.MoveModel
import vf.poke.core.model.enumeration.{CriticalRate, MoveCategory, PokeType}
import vf.poke.core.model.stored.randomization.Move

object UniqueMoveAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueMoveAccess = new _UniqueMoveAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueMoveAccess(condition: Condition) extends UniqueMoveAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct moves.
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniqueMoveAccess 
	extends UniqueMoveAccessLike[Move] with SingleRowModelAccess[Move] with FilterableView[UniqueMoveAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = MoveFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueMoveAccess = 
		new UniqueMoveAccess._UniqueMoveAccess(mergeCondition(filterCondition))
}

