package vf.poke.core.database.access.single.randomization.starter_set

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.StarterSetFactory
import vf.poke.core.database.model.randomization.StarterSetModel
import vf.poke.core.model.stored.randomization.StarterSet

/**
  * Used for accessing individual starter sets
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbStarterSet extends SingleRowModelAccess[StarterSet] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = StarterSetModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StarterSetFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted starter set
	  * @return An access point to that starter set
	  */
	def apply(id: Int) = DbSingleStarterSet(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique starter sets.
	  * @return An access point to the starter set that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniqueStarterSetAccess(mergeCondition(condition))
}

