package vf.poke.core.database.access.single.randomization.starter_assignment

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.StarterAssignmentFactory
import vf.poke.core.database.model.randomization.StarterAssignmentModel
import vf.poke.core.model.stored.randomization.StarterAssignment

/**
  * Used for accessing individual starter assignments
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbStarterAssignment extends SingleRowModelAccess[StarterAssignment] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = StarterAssignmentModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StarterAssignmentFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted starter assignment
	  * @return An access point to that starter assignment
	  */
	def apply(id: Int) = DbSingleStarterAssignment(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique starter assignments.
	  * @return An access point to the starter assignment that satisfies the specified condition
	  */
	protected
		 def filterDistinct(condition: Condition) = UniqueStarterAssignmentAccess(mergeCondition(condition))
}

