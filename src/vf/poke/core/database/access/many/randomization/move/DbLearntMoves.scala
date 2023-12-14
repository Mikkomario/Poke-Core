package vf.poke.core.database.access.many.randomization.move

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple learnt moves at a time
  * @author Mikko Hilpinen
  * @since 13.12.2023, v1.0
  */
object DbLearntMoves extends ManyLearntMovesAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted learnt moves
	  * @return An access point to learnt moves with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbLearntMovesSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbLearntMovesSubset(targetIds: Set[Int]) extends ManyLearntMovesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

