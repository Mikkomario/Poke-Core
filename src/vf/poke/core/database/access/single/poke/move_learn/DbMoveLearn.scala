package vf.poke.core.database.access.single.poke.move_learn

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.MoveLearnFactory
import vf.poke.core.database.model.poke.MoveLearnModel
import vf.poke.core.model.stored.poke.MoveLearn

/**
  * Used for accessing individual move learns
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbMoveLearn extends SingleRowModelAccess[MoveLearn] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = MoveLearnModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = MoveLearnFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted move learn
	  * @return An access point to that move learn
	  */
	def apply(id: Int) = DbSingleMoveLearn(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique move learns.
	  * @return An access point to the move learn that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniqueMoveLearnAccess(mergeCondition(condition))
}

