package vf.poke.core.database.access.many.poke.stat

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple poke stats at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbPokeStats extends ManyPokeStatsAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted poke stats
	  * @return An access point to poke stats with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbPokeStatsSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbPokeStatsSubset(targetIds: Set[Int]) extends ManyPokeStatsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

