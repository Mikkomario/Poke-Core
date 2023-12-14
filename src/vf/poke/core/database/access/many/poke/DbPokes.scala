package vf.poke.core.database.access.many.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple pokes at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbPokes extends ManyPokesAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted pokes
	  * @return An access point to pokes with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbPokesSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbPokesSubset(targetIds: Set[Int]) extends ManyPokesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

