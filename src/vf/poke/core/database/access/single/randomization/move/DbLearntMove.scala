package vf.poke.core.database.access.single.randomization.move

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.LearntMoveFactory
import vf.poke.core.database.model.poke.MoveLearnModel
import vf.poke.core.database.model.randomization.MoveModel
import vf.poke.core.model.combined.randomization.LearntMove

/**
  * Used for accessing individual learnt moves
  * @author Mikko Hilpinen
  * @since 13.12.2023, v1.0
  */
object DbLearntMove extends SingleRowModelAccess[LearntMove] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with linked moves
	  */
	protected def model = MoveModel
	
	/**
	  * A database model (factory) used for interacting with the linked learn
	  */
	protected def learnModel = MoveLearnModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = LearntMoveFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted learnt move
	  * @return An access point to that learnt move
	  */
	def apply(id: Int) = DbSingleLearntMove(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique learnt moves.
	  * @return An access point to the learnt move that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniqueLearntMoveAccess(mergeCondition(condition))
}

