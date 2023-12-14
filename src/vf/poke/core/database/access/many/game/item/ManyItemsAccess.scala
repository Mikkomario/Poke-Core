package vf.poke.core.database.access.many.game.item

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.game.ItemFactory
import vf.poke.core.database.model.game.ItemModel
import vf.poke.core.model.stored.game.Item

object ManyItemsAccess
{
	// NESTED	--------------------
	
	private class ManyItemsSubView(condition: Condition) extends ManyItemsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple items at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait ManyItemsAccess extends ManyRowModelAccess[Item] with FilterableView[ManyItemsAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * game ids of the accessible items
	  */
	def gameIds(implicit connection: Connection) = pullColumn(model.gameIdColumn).map { v => v.getInt }
	
	/**
	  * index in games of the accessible items
	  */
	def indicesInGames(implicit connection: Connection) = pullColumn(model.indexInGameColumn)
		.map { v => v.getInt }
	
	/**
	  * names of the accessible items
	  */
	def names(implicit connection: Connection) = pullColumn(model.nameColumn).flatMap { _.string }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = ItemModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ItemFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyItemsAccess = 
		new ManyItemsAccess.ManyItemsSubView(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	 * @param gameId Id of the targeted game
	 * @return Access to items in that game
	 */
	def inGame(gameId: Int) = filter(model.withGameId(gameId).toCondition)
	/**
	 * @param inGameIds In-game item ids
	 * @return Access to items with those ids
	 */
	def withInGameIds(inGameIds: Iterable[Int]) = filter(model.indexInGameColumn.in(inGameIds))
	
	/**
	  * Updates the game ids of the targeted items
	  * @param newGameId A new game id to assign
	  * @return Whether any item was affected
	  */
	def gameIds_=(newGameId: Int)(implicit connection: Connection) = putColumn(model.gameIdColumn, newGameId)
	
	/**
	  * Updates the index in games of the targeted items
	  * @param newIndexInGame A new index in game to assign
	  * @return Whether any item was affected
	  */
	def indexInGames_=(newIndexInGame: Int)(implicit connection: Connection) = 
		putColumn(model.indexInGameColumn, newIndexInGame)
	
	/**
	  * Updates the names of the targeted items
	  * @param newName A new name to assign
	  * @return Whether any item was affected
	  */
	def names_=(newName: String)(implicit connection: Connection) = putColumn(model.nameColumn, newName)
}

