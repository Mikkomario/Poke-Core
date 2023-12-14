package vf.poke.core.database.access.single.randomization.starter_assignment

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.StarterAssignmentFactory
import vf.poke.core.database.model.randomization.StarterAssignmentModel
import vf.poke.core.model.stored.randomization.StarterAssignment

object UniqueStarterAssignmentAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueStarterAssignmentAccess =
		 new _UniqueStarterAssignmentAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueStarterAssignmentAccess(condition: Condition) extends UniqueStarterAssignmentAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct starter assignments.
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniqueStarterAssignmentAccess 
	extends SingleRowModelAccess[StarterAssignment] with FilterableView[UniqueStarterAssignmentAccess] 
		with DistinctModelAccess[StarterAssignment, Option[StarterAssignment], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * 
		Id of the starter set to which this starter belongs. None if no starter assignment (or value) was found.
	  */
	def setId(implicit connection: Connection) = pullColumn(model.setIdColumn).int
	
	/**
	  * Id of the poke that appears as a starter. None if no starter assignment (or value) was found.
	  */
	def pokeId(implicit connection: Connection) = pullColumn(model.pokeIdColumn).int
	
	/**
	  * 
		A zero-based index that shows where this starter appears relative to the others. The following index is
	  *  typically strong against the previous index.. None if no starter assignment (or value) was found.
	  */
	def placement(implicit connection: Connection) = pullColumn(model.placementColumn).int
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = StarterAssignmentModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StarterAssignmentFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueStarterAssignmentAccess = 
		new UniqueStarterAssignmentAccess._UniqueStarterAssignmentAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the placements of the targeted starter assignments
	  * @param newPlacement A new placement to assign
	  * @return Whether any starter assignment was affected
	  */
	def placement_=(newPlacement: Int)(implicit connection: Connection) = 
		putColumn(model.placementColumn, newPlacement)
	
	/**
	  * Updates the poke ids of the targeted starter assignments
	  * @param newPokeId A new poke id to assign
	  * @return Whether any starter assignment was affected
	  */
	def pokeId_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
	
	/**
	  * Updates the set ids of the targeted starter assignments
	  * @param newSetId A new set id to assign
	  * @return Whether any starter assignment was affected
	  */
	def setId_=(newSetId: Int)(implicit connection: Connection) = putColumn(model.setIdColumn, newSetId)
}

