package vf.poke.core.database.access.single.poke.evo

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.EvoFactory
import vf.poke.core.database.model.poke.EvoModel
import vf.poke.core.model.stored.poke.Evo

/**
  * Used for accessing individual evos
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbEvo extends SingleRowModelAccess[Evo] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = EvoModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = EvoFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted evo
	  * @return An access point to that evo
	  */
	def apply(id: Int) = DbSingleEvo(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique evos.
	  * @return An access point to the evo that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniqueEvoAccess(mergeCondition(condition))
}

