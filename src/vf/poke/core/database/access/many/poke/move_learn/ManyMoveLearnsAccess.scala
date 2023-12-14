package vf.poke.core.database.access.many.poke.move_learn

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.MoveLearnFactory
import vf.poke.core.database.model.poke.MoveLearnModel
import vf.poke.core.model.stored.poke.MoveLearn

object ManyMoveLearnsAccess
{
	// NESTED	--------------------
	
	private class ManyMoveLearnsSubView(condition: Condition) extends ManyMoveLearnsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple move learns at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait ManyMoveLearnsAccess 
	extends ManyRowModelAccess[MoveLearn] with FilterableView[ManyMoveLearnsAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * poke ids of the accessible move learns
	  */
	def pokeIds(implicit connection: Connection) = pullColumn(model.pokeIdColumn).map { v => v.getInt }
	
	/**
	  * move ids of the accessible move learns
	  */
	def moveIds(implicit connection: Connection) = pullColumn(model.moveIdColumn).map { v => v.getInt }
	
	/**
	  * levels of the accessible move learns
	  */
	def levels(implicit connection: Connection) = pullColumn(model.levelColumn).map { v => v.getInt }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = MoveLearnModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = MoveLearnFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyMoveLearnsAccess = 
		new ManyMoveLearnsAccess.ManyMoveLearnsSubView(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the levels of the targeted move learns
	  * @param newLevel A new level to assign
	  * @return Whether any move learn was affected
	  */
	def levels_=(newLevel: Int)(implicit connection: Connection) = putColumn(model.levelColumn, newLevel)
	
	/**
	  * Updates the move ids of the targeted move learns
	  * @param newMoveId A new move id to assign
	  * @return Whether any move learn was affected
	  */
	def moveIds_=(newMoveId: Int)(implicit connection: Connection) = putColumn(model.moveIdColumn, newMoveId)
	
	/**
	  * Updates the poke ids of the targeted move learns
	  * @param newPokeId A new poke id to assign
	  * @return Whether any move learn was affected
	  */
	def pokeIds_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
}

