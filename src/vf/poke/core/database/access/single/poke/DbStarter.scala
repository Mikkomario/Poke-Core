package vf.poke.core.database.access.single.poke

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.StarterFactory
import vf.poke.core.database.model.poke.PokeModel
import vf.poke.core.database.model.randomization.StarterAssignmentModel
import vf.poke.core.model.combined.poke.Starter

/**
  * Used for accessing individual starters
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbStarter extends SingleRowModelAccess[Starter] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with linked pokes
	  */
	protected def model = PokeModel
	
	/**
	  * A database model (factory) used for interacting with the linked starter assignment
	  */
	protected def starterAssignmentModel = StarterAssignmentModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StarterFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted starter
	  * @return An access point to that starter
	  */
	def apply(id: Int) = DbSingleStarter(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique starters.
	  * @return An access point to the starter that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniqueStarterAccess(mergeCondition(condition))
}

