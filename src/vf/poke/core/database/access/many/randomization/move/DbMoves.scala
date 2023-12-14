package vf.poke.core.database.access.many.randomization.move

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple moves at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbMoves extends ManyMovesAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted moves
	  * @return An access point to moves with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbMovesSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbMovesSubset(targetIds: Set[Int]) extends ManyMovesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

