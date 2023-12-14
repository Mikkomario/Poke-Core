package vf.poke.companion.database.access.single.gameplay.type_guess

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.poke.companion.controller.app.PokeRunEnvironment
import vf.poke.companion.database.factory.gameplay.TypeGuessFactory
import vf.poke.companion.database.model.gameplay.TypeGuessModel
import vf.poke.companion.model.stored.gameplay.TypeGuess

/**
  * Used for accessing individual type guessses
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object DbTypeGuess extends SingleRowModelAccess[TypeGuess] with NonDeprecatedView[TypeGuess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = TypeGuessModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = TypeGuessFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted type guess
	  * @return An access point to that type guess
	  */
	def apply(id: Int) = DbSingleTypeGuess(id)
	
	/**
	 * @param pokeId Id of the targeted poke
	 * @param env Implicit environment
	 * @return Access to that poke's latest type guess
	 */
	def forPoke(pokeId: Int)(implicit env: PokeRunEnvironment) =
		filterDistinct(model.withRunId(env.run.id).withPokeId(pokeId).toCondition)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique type guessses.
	  * @return An access point to the type guess that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniqueTypeGuessAccess(mergeCondition(condition))
}

