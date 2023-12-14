package vf.poke.companion.database.access.single.gameplay.poke_training

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.poke.companion.controller.app.PokeRunEnvironment
import vf.poke.companion.database.factory.gameplay.PokeTrainingFactory
import vf.poke.companion.database.model.gameplay.PokeTrainingModel
import vf.poke.companion.model.stored.gameplay.PokeTraining

/**
  * Used for accessing individual poke trainings
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object DbPokeTraining 
	extends SingleRowModelAccess[PokeTraining] with NonDeprecatedView[PokeTraining] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = PokeTrainingModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PokeTrainingFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted poke training
	  * @return An access point to that poke training
	  */
	def apply(id: Int) = DbSinglePokeTraining(id)
	
	/**
	 * @param pokeId Id of the targeted poke
	 * @param env Implicit run environment
	 * @return Access to that poke's latest training info on this run
	 */
	def ofPoke(pokeId: Int)(implicit env: PokeRunEnvironment) =
		filterDistinct(model.withRunId(env.run.id).withPokeId(pokeId).toCondition)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique poke trainings.
	  * @return An access point to the poke training that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniquePokeTrainingAccess(mergeCondition(condition))
}

