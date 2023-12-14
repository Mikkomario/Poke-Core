package vf.poke.core.database.access.single.randomization

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.RandomizationFactory
import vf.poke.core.database.model.randomization.RandomizationModel
import vf.poke.core.model.stored.randomization.Randomization

/**
  * Used for accessing individual randomizations
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbRandomization extends SingleRowModelAccess[Randomization] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = RandomizationModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = RandomizationFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted randomization
	  * @return An access point to that randomization
	  */
	def apply(id: Int) = DbSingleRandomization(id)
	
	/**
	 * @param gameId Id of the targeted game instance
	 * @return Access to the non-modified randomization
	 */
	def original(gameId: Int) =
		filterDistinct(model.withGameId(gameId).withIsOriginal(true).toCondition)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique randomizations.
	  * @return An access point to the randomization that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniqueRandomizationAccess(mergeCondition(condition))
}

