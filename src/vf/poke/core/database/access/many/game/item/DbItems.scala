package vf.poke.core.database.access.many.game.item

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple items at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbItems extends ManyItemsAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted items
	  * @return An access point to items with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbItemsSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbItemsSubset(targetIds: Set[Int]) extends ManyItemsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

