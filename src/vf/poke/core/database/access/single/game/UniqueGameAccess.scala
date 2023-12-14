package vf.poke.core.database.access.single.game

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.game.GameFactory
import vf.poke.core.database.model.game.GameModel
import vf.poke.core.model.stored.game.Game

object UniqueGameAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueGameAccess = new _UniqueGameAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueGameAccess(condition: Condition) extends UniqueGameAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct games.
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniqueGameAccess 
	extends SingleRowModelAccess[Game] with FilterableView[UniqueGameAccess] 
		with DistinctModelAccess[Game, Option[Game], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Name of this game. None if no game (or value) was found.
	  */
	def name(implicit connection: Connection) = pullColumn(model.nameColumn).getString
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = GameModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = GameFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueGameAccess = 
		new UniqueGameAccess._UniqueGameAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the names of the targeted games
	  * @param newName A new name to assign
	  * @return Whether any game was affected
	  */
	def name_=(newName: String)(implicit connection: Connection) = putColumn(model.nameColumn, newName)
}

