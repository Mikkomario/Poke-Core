package vf.poke.core.database.access.single.randomization.move

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.MoveFactory
import vf.poke.core.database.model.randomization.MoveModel
import vf.poke.core.model.stored.randomization.Move

/**
  * Used for accessing individual moves
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbMove extends SingleRowModelAccess[Move] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = MoveModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = MoveFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted move
	  * @return An access point to that move
	  */
	def apply(id: Int) = DbSingleMove(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique moves.
	  * @return An access point to the move that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniqueMoveAccess(mergeCondition(condition))
}

