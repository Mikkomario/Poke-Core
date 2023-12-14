package vf.poke.core.database.access.single.randomization.move

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.LearntMoveFactory
import vf.poke.core.database.model.poke.MoveLearnModel
import vf.poke.core.model.combined.randomization.LearntMove

object UniqueLearntMoveAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueLearntMoveAccess = new _UniqueLearntMoveAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueLearntMoveAccess(condition: Condition) extends UniqueLearntMoveAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return distinct learnt moves
  * @author Mikko Hilpinen
  * @since 13.12.2023, v1.0
  */
trait UniqueLearntMoveAccess 
	extends UniqueMoveAccessLike[LearntMove] with SingleRowModelAccess[LearntMove] 
		with FilterableView[UniqueLearntMoveAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the poke that learns the move. None if no move learn (or value) was found.
	  */
	def learnPokeId(implicit connection: Connection) = pullColumn(learnModel.pokeIdColumn).int
	
	/**
	  * Id of the move learnt. None if no move learn (or value) was found.
	  */
	def learnMoveId(implicit connection: Connection) = pullColumn(learnModel.moveIdColumn).int
	
	/**
	  * Level at which this move is learnt, if applicable.. None if no move learn (or value) was found.
	  */
	def learnLevel(implicit connection: Connection) = pullColumn(learnModel.levelColumn).int
	
	/**
	  * A database model (factory) used for interacting with the linked learn
	  */
	protected def learnModel = MoveLearnModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = LearntMoveFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueLearntMoveAccess = 
		new UniqueLearntMoveAccess._UniqueLearntMoveAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the levels of the targeted move learns
	  * @param newLevel A new level to assign
	  * @return Whether any move learn was affected
	  */
	def learnLevel_=(newLevel: Int)(implicit connection: Connection) = putColumn(learnModel.levelColumn, 
		newLevel)
	
	/**
	  * Updates the move ids of the targeted move learns
	  * @param newMoveId A new move id to assign
	  * @return Whether any move learn was affected
	  */
	def learnMoveId_=(newMoveId: Int)(implicit connection: Connection) = 
		putColumn(learnModel.moveIdColumn, newMoveId)
	
	/**
	  * Updates the poke ids of the targeted move learns
	  * @param newPokeId A new poke id to assign
	  * @return Whether any move learn was affected
	  */
	def learnPokeId_=(newPokeId: Int)(implicit connection: Connection) = 
		putColumn(learnModel.pokeIdColumn, newPokeId)
}

