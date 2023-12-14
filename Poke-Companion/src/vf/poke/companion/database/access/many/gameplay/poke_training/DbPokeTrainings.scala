package vf.poke.companion.database.access.many.gameplay.poke_training

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView}
import vf.poke.companion.model.stored.gameplay.PokeTraining

/**
  * The root access point when targeting multiple poke trainings at a time
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object DbPokeTrainings extends ManyPokeTrainingsAccess with NonDeprecatedView[PokeTraining]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) poke trainings
	  */
	def includingHistory = DbPokeTrainingsIncludingHistory
	
	
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted poke trainings
	  * @return An access point to poke trainings with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbPokeTrainingsSubset(ids)
	
	
	// NESTED	--------------------
	
	object DbPokeTrainingsIncludingHistory extends ManyPokeTrainingsAccess with UnconditionalView
	
	class DbPokeTrainingsSubset(targetIds: Set[Int]) extends ManyPokeTrainingsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

