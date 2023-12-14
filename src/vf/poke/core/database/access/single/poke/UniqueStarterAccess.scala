package vf.poke.core.database.access.single.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.StarterFactory
import vf.poke.core.database.model.randomization.StarterAssignmentModel
import vf.poke.core.model.combined.poke.Starter

object UniqueStarterAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueStarterAccess = new _UniqueStarterAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueStarterAccess(condition: Condition) extends UniqueStarterAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return distinct starters
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniqueStarterAccess 
	extends UniquePokeAccessLike[Starter] with SingleRowModelAccess[Starter] 
		with FilterableView[UniqueStarterAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * 
		Id of the starter set to which this starter belongs. None if no starter assignment (or value) was found.
	  */
	def starterAssignmentSetId(implicit connection: Connection) = 
		pullColumn(starterAssignmentModel.setIdColumn).int
	
	/**
	  * Id of the poke that appears as a starter. None if no starter assignment (or value) was found.
	  */
	def starterAssignmentPokeId(implicit connection: Connection) = 
		pullColumn(starterAssignmentModel.pokeIdColumn).int
	
	/**
	  * 
		A zero-based index that shows where this starter appears relative to the others. The following index is
	  *  typically strong against the previous index.. None if no starter assignment (or value) was found.
	  */
	def starterAssignmentPlacement(implicit connection: Connection) = 
		pullColumn(starterAssignmentModel.placementColumn).int
	
	/**
	  * A database model (factory) used for interacting with the linked starter assignment
	  */
	protected def starterAssignmentModel = StarterAssignmentModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StarterFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueStarterAccess = 
		new UniqueStarterAccess._UniqueStarterAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the placements of the targeted starter assignments
	  * @param newPlacement A new placement to assign
	  * @return Whether any starter assignment was affected
	  */
	def starterAssignmentPlacement_=(newPlacement: Int)(implicit connection: Connection) = 
		putColumn(starterAssignmentModel.placementColumn, newPlacement)
	
	/**
	  * Updates the poke ids of the targeted starter assignments
	  * @param newPokeId A new poke id to assign
	  * @return Whether any starter assignment was affected
	  */
	def starterAssignmentPokeId_=(newPokeId: Int)(implicit connection: Connection) = 
		putColumn(starterAssignmentModel.pokeIdColumn, newPokeId)
	
	/**
	  * Updates the set ids of the targeted starter assignments
	  * @param newSetId A new set id to assign
	  * @return Whether any starter assignment was affected
	  */
	def starterAssignmentSetId_=(newSetId: Int)(implicit connection: Connection) = 
		putColumn(starterAssignmentModel.setIdColumn, newSetId)
}

