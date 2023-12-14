package vf.poke.core.database.access.many.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple starters at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbStarters extends ManyStartersAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted starters
	  * @return An access point to starters with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbStartersSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbStartersSubset(targetIds: Set[Int]) extends ManyStartersAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

