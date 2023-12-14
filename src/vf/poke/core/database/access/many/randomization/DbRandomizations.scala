package vf.poke.core.database.access.many.randomization

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple randomizations at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbRandomizations extends ManyRandomizationsAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted randomizations
	  * @return An access point to randomizations with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbRandomizationsSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbRandomizationsSubset(targetIds: Set[Int]) extends ManyRandomizationsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

