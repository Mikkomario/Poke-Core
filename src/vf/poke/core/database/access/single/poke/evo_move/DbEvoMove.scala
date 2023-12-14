package vf.poke.core.database.access.single.poke.evo_move

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.EvoMoveFactory
import vf.poke.core.database.model.poke.EvoMoveModel
import vf.poke.core.model.stored.poke.EvoMove

/**
  * Used for accessing individual evo moves
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbEvoMove extends SingleRowModelAccess[EvoMove] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = EvoMoveModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = EvoMoveFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted evo move
	  * @return An access point to that evo move
	  */
	def apply(id: Int) = DbSingleEvoMove(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique evo moves.
	  * @return An access point to the evo move that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniqueEvoMoveAccess(mergeCondition(condition))
}

