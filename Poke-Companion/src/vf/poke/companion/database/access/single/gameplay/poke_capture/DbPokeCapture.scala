package vf.poke.companion.database.access.single.gameplay.poke_capture

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.companion.controller.app.PokeRunEnvironment
import vf.poke.companion.database.factory.gameplay.PokeCaptureFactory
import vf.poke.companion.database.model.gameplay.PokeCaptureModel
import vf.poke.companion.model.stored.gameplay.PokeCapture

/**
  * Used for accessing individual poke captures
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object DbPokeCapture extends SingleRowModelAccess[PokeCapture] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = PokeCaptureModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PokeCaptureFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted poke capture
	  * @return An access point to that poke capture
	  */
	def apply(id: Int) = DbSinglePokeCapture(id)
	
	/**
	 * @param pokeId Id of the targeted poke
	 * @param env Implicit run data
	 * @return Access to that poke's original capture data
	 */
	def ofPoke(pokeId: Int)(implicit env: PokeRunEnvironment) =
		filterDistinct(model.withRunId(env.run.id).withPokeId(pokeId).toCondition)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique poke captures.
	  * @return An access point to the poke capture that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniquePokeCaptureAccess(mergeCondition(condition))
}

