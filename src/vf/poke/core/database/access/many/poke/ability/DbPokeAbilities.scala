package vf.poke.core.database.access.many.poke.ability

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple poke abilities at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbPokeAbilities extends ManyPokeAbilitiesAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted poke abilities
	  * @return An access point to poke abilities with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbPokeAbilitiesSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbPokeAbilitiesSubset(targetIds: Set[Int]) extends ManyPokeAbilitiesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

