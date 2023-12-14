package vf.poke.core.database.access.many.poke.evo

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple evos at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbEvos extends ManyEvosAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted evos
	  * @return An access point to evos with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbEvosSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbEvosSubset(targetIds: Set[Int]) extends ManyEvosAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

