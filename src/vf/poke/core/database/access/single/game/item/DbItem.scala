package vf.poke.core.database.access.single.game.item

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.game.ItemFactory
import vf.poke.core.database.model.game.ItemModel
import vf.poke.core.model.stored.game.Item

/**
  * Used for accessing individual items
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbItem extends SingleRowModelAccess[Item] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = ItemModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ItemFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted item
	  * @return An access point to that item
	  */
	def apply(id: Int) = DbSingleItem(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique items.
	  * @return An access point to the item that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniqueItemAccess(mergeCondition(condition))
}

