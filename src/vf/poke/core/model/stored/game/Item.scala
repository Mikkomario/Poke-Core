package vf.poke.core.model.stored.game

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.core.database.access.single.game.item.DbSingleItem
import vf.poke.core.model.partial.game.ItemData

/**
  * Represents a item that has already been stored in the database
  * @param id id of this item in the database
  * @param data Wrapped item data
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class Item(id: Int, data: ItemData) extends StoredModelConvertible[ItemData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this item in the database
	  */
	def access = DbSingleItem(id)
}

