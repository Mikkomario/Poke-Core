package vf.poke.core.database.access.many.game.ability

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple abilities at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbAbilities extends ManyAbilitiesAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted abilities
	  * @return An access point to abilities with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbAbilitiesSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbAbilitiesSubset(targetIds: Set[Int]) extends ManyAbilitiesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

