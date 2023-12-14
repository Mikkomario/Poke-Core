package vf.poke.core.database.access.many.poke.move_learn

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple move learns at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbMoveLearns extends ManyMoveLearnsAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted move learns
	  * @return An access point to move learns with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbMoveLearnsSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbMoveLearnsSubset(targetIds: Set[Int]) extends ManyMoveLearnsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

