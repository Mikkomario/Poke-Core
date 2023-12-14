package vf.poke.core.database.access.single.poke.move_learn

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.MoveLearnFactory
import vf.poke.core.database.model.poke.MoveLearnModel
import vf.poke.core.model.stored.poke.MoveLearn

object UniqueMoveLearnAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueMoveLearnAccess = new _UniqueMoveLearnAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueMoveLearnAccess(condition: Condition) extends UniqueMoveLearnAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct move learns.
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniqueMoveLearnAccess 
	extends SingleRowModelAccess[MoveLearn] with FilterableView[UniqueMoveLearnAccess] 
		with DistinctModelAccess[MoveLearn, Option[MoveLearn], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the poke that learns the move. None if no move learn (or value) was found.
	  */
	def pokeId(implicit connection: Connection) = pullColumn(model.pokeIdColumn).int
	
	/**
	  * Id of the move learnt. None if no move learn (or value) was found.
	  */
	def moveId(implicit connection: Connection) = pullColumn(model.moveIdColumn).int
	
	/**
	  * Level at which this move is learnt, if applicable.. None if no move learn (or value) was found.
	  */
	def level(implicit connection: Connection) = pullColumn(model.levelColumn).int
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = MoveLearnModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = MoveLearnFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueMoveLearnAccess = 
		new UniqueMoveLearnAccess._UniqueMoveLearnAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the levels of the targeted move learns
	  * @param newLevel A new level to assign
	  * @return Whether any move learn was affected
	  */
	def level_=(newLevel: Int)(implicit connection: Connection) = putColumn(model.levelColumn, newLevel)
	
	/**
	  * Updates the move ids of the targeted move learns
	  * @param newMoveId A new move id to assign
	  * @return Whether any move learn was affected
	  */
	def moveId_=(newMoveId: Int)(implicit connection: Connection) = putColumn(model.moveIdColumn, newMoveId)
	
	/**
	  * Updates the poke ids of the targeted move learns
	  * @param newPokeId A new poke id to assign
	  * @return Whether any move learn was affected
	  */
	def pokeId_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
}

