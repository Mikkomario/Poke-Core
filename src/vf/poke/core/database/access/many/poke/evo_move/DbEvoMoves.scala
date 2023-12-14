package vf.poke.core.database.access.many.poke.evo_move

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple evo moves at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbEvoMoves extends ManyEvoMovesAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted evo moves
	  * @return An access point to evo moves with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbEvoMovesSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbEvoMovesSubset(targetIds: Set[Int]) extends ManyEvoMovesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

