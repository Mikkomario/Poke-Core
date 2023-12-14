package vf.poke.core.database.access.many.poke.ability

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple detailed poke abilities at a time
  * @author Mikko Hilpinen
  * @since 13.12.2023, v1.0
  */
object DbDetailedPokeAbilities extends ManyDetailedPokeAbilitiesAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted detailed poke abilities
	  * @return An access point to detailed poke abilities with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbDetailedPokeAbilitiesSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbDetailedPokeAbilitiesSubset(targetIds: Set[Int]) extends ManyDetailedPokeAbilitiesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

