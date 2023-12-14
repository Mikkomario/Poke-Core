package vf.poke.companion.database.access.many.gameplay.attack_experiment

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple attack experiments at a time
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object DbAttackExperiments extends ManyAttackExperimentsAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted attack experiments
	  * @return An access point to attack experiments with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbAttackExperimentsSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbAttackExperimentsSubset(targetIds: Set[Int]) extends ManyAttackExperimentsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

