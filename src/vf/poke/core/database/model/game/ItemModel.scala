package vf.poke.core.database.model.game

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.core.database.factory.game.ItemFactory
import vf.poke.core.model.partial.game.ItemData
import vf.poke.core.model.stored.game.Item

/**
  * Used for constructing ItemModel instances and for inserting items to the database
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object ItemModel extends DataInserter[ItemModel, Item, ItemData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains item game id
	  */
	val gameIdAttName = "gameId"
	
	/**
	  * Name of the property that contains item index in game
	  */
	val indexInGameAttName = "indexInGame"
	
	/**
	  * Name of the property that contains item name
	  */
	val nameAttName = "name"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains item game id
	  */
	def gameIdColumn = table(gameIdAttName)
	
	/**
	  * Column that contains item index in game
	  */
	def indexInGameColumn = table(indexInGameAttName)
	
	/**
	  * Column that contains item name
	  */
	def nameColumn = table(nameAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = ItemFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: ItemData) = apply(None, Some(data.gameId), Some(data.indexInGame), data.name)
	
	override protected def complete(id: Value, data: ItemData) = Item(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param gameId Id of the game in which this item appears
	  * @return A model containing only the specified game id
	  */
	def withGameId(gameId: Int) = apply(gameId = Some(gameId))
	
	/**
	  * @param id A item id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param indexInGame Index of this item within the game
	  * @return A model containing only the specified index in game
	  */
	def withIndexInGame(indexInGame: Int) = apply(indexInGame = Some(indexInGame))
	
	/**
	  * @param name Name of this item in the game
	  * @return A model containing only the specified name
	  */
	def withName(name: String) = apply(name = name)
}

/**
  * Used for interacting with Items in the database
  * @param id item database id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class ItemModel(id: Option[Int] = None, gameId: Option[Int] = None, indexInGame: Option[Int] = None, 
	name: String = "") 
	extends StorableWithFactory[Item]
{
	// IMPLEMENTED	--------------------
	
	override def factory = ItemModel.factory
	
	override def valueProperties = {
		import ItemModel._
		Vector("id" -> id, gameIdAttName -> gameId, indexInGameAttName -> indexInGame, nameAttName -> name)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param gameId Id of the game in which this item appears
	  * @return A new copy of this model with the specified game id
	  */
	def withGameId(gameId: Int) = copy(gameId = Some(gameId))
	
	/**
	  * @param indexInGame Index of this item within the game
	  * @return A new copy of this model with the specified index in game
	  */
	def withIndexInGame(indexInGame: Int) = copy(indexInGame = Some(indexInGame))
	
	/**
	  * @param name Name of this item in the game
	  * @return A new copy of this model with the specified name
	  */
	def withName(name: String) = copy(name = name)
}

