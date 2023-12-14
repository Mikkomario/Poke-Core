package vf.poke.core.database.access.single.randomization.starter_set

import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.DetailedStarterSetFactory
import vf.poke.core.database.model.randomization.{StarterAssignmentModel, StarterSetModel}
import vf.poke.core.model.combined.randomization.DetailedStarterSet

/**
  * Used for accessing individual detailed starter sets
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbDetailedStarterSet extends SingleModelAccess[DetailedStarterSet] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with linked sets
	  */
	protected def model = StarterSetModel
	
	/**
	  * A database model (factory) used for interacting with the linked assignments
	  */
	protected def assignmentModel = StarterAssignmentModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = DetailedStarterSetFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted detailed starter set
	  * @return An access point to that detailed starter set
	  */
	def apply(id: Int) = DbSingleDetailedStarterSet(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique detailed starter sets.
	  * @return An access point to the detailed starter set that satisfies the specified condition
	  */
	protected
		 def filterDistinct(condition: Condition) = UniqueDetailedStarterSetAccess(mergeCondition(condition))
}

