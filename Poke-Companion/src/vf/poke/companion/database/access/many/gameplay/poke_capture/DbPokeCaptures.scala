package vf.poke.companion.database.access.many.gameplay.poke_capture

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple poke captures at a time
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object DbPokeCaptures extends ManyPokeCapturesAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted poke captures
	  * @return An access point to poke captures with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbPokeCapturesSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbPokeCapturesSubset(targetIds: Set[Int]) extends ManyPokeCapturesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

