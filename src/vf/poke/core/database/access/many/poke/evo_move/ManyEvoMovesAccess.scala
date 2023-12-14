package vf.poke.core.database.access.many.poke.evo_move

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.EvoMoveFactory
import vf.poke.core.database.model.poke.EvoMoveModel
import vf.poke.core.model.stored.poke.EvoMove

object ManyEvoMovesAccess
{
	// NESTED	--------------------
	
	private class ManyEvoMovesSubView(condition: Condition) extends ManyEvoMovesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple evo moves at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait ManyEvoMovesAccess 
	extends ManyRowModelAccess[EvoMove] with FilterableView[ManyEvoMovesAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * poke ids of the accessible evo moves
	  */
	def pokeIds(implicit connection: Connection) = pullColumn(model.pokeIdColumn).map { v => v.getInt }
	
	/**
	  * move ids of the accessible evo moves
	  */
	def moveIds(implicit connection: Connection) = pullColumn(model.moveIdColumn).map { v => v.getInt }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = EvoMoveModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = EvoMoveFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyEvoMovesAccess = 
		new ManyEvoMovesAccess.ManyEvoMovesSubView(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the move ids of the targeted evo moves
	  * @param newMoveId A new move id to assign
	  * @return Whether any evo move was affected
	  */
	def moveIds_=(newMoveId: Int)(implicit connection: Connection) = putColumn(model.moveIdColumn, newMoveId)
	
	/**
	  * Updates the poke ids of the targeted evo moves
	  * @param newPokeId A new poke id to assign
	  * @return Whether any evo move was affected
	  */
	def pokeIds_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
}

