package vf.poke.core.database.access.single.game.item

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.game.ItemFactory
import vf.poke.core.database.model.game.ItemModel
import vf.poke.core.model.stored.game.Item

object UniqueItemAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueItemAccess = new _UniqueItemAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueItemAccess(condition: Condition) extends UniqueItemAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct items.
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniqueItemAccess 
	extends SingleRowModelAccess[Item] with FilterableView[UniqueItemAccess] 
		with DistinctModelAccess[Item, Option[Item], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the game in which this item appears. None if no item (or value) was found.
	  */
	def gameId(implicit connection: Connection) = pullColumn(model.gameIdColumn).int
	
	/**
	  * Index of this item within the game. None if no item (or value) was found.
	  */
	def indexInGame(implicit connection: Connection) = pullColumn(model.indexInGameColumn).int
	
	/**
	  * Name of this item in the game. None if no item (or value) was found.
	  */
	def name(implicit connection: Connection) = pullColumn(model.nameColumn).getString
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = ItemModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ItemFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueItemAccess = 
		new UniqueItemAccess._UniqueItemAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the game ids of the targeted items
	  * @param newGameId A new game id to assign
	  * @return Whether any item was affected
	  */
	def gameId_=(newGameId: Int)(implicit connection: Connection) = putColumn(model.gameIdColumn, newGameId)
	
	/**
	  * Updates the index in games of the targeted items
	  * @param newIndexInGame A new index in game to assign
	  * @return Whether any item was affected
	  */
	def indexInGame_=(newIndexInGame: Int)(implicit connection: Connection) = 
		putColumn(model.indexInGameColumn, newIndexInGame)
	
	/**
	  * Updates the names of the targeted items
	  * @param newName A new name to assign
	  * @return Whether any item was affected
	  */
	def name_=(newName: String)(implicit connection: Connection) = putColumn(model.nameColumn, newName)
}

