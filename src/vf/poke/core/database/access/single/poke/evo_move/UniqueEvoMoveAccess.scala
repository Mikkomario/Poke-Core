package vf.poke.core.database.access.single.poke.evo_move

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.EvoMoveFactory
import vf.poke.core.database.model.poke.EvoMoveModel
import vf.poke.core.model.stored.poke.EvoMove

object UniqueEvoMoveAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueEvoMoveAccess = new _UniqueEvoMoveAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueEvoMoveAccess(condition: Condition) extends UniqueEvoMoveAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct evo moves.
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniqueEvoMoveAccess 
	extends SingleRowModelAccess[EvoMove] with FilterableView[UniqueEvoMoveAccess] 
		with DistinctModelAccess[EvoMove, Option[EvoMove], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the poke (form) learning this move. None if no evo move (or value) was found.
	  */
	def pokeId(implicit connection: Connection) = pullColumn(model.pokeIdColumn).int
	
	/**
	  * id of the move learnt. None if no evo move (or value) was found.
	  */
	def moveId(implicit connection: Connection) = pullColumn(model.moveIdColumn).int
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = EvoMoveModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = EvoMoveFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueEvoMoveAccess = 
		new UniqueEvoMoveAccess._UniqueEvoMoveAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the move ids of the targeted evo moves
	  * @param newMoveId A new move id to assign
	  * @return Whether any evo move was affected
	  */
	def moveId_=(newMoveId: Int)(implicit connection: Connection) = putColumn(model.moveIdColumn, newMoveId)
	
	/**
	  * Updates the poke ids of the targeted evo moves
	  * @param newPokeId A new poke id to assign
	  * @return Whether any evo move was affected
	  */
	def pokeId_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
}

