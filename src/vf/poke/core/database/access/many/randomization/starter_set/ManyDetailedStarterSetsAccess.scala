package vf.poke.core.database.access.many.randomization.starter_set

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.DetailedStarterSetFactory
import vf.poke.core.database.model.randomization.StarterAssignmentModel
import vf.poke.core.model.combined.randomization.DetailedStarterSet

object ManyDetailedStarterSetsAccess
{
	// NESTED	--------------------
	
	private class SubAccess(condition: Condition) extends ManyDetailedStarterSetsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return multiple detailed starter sets at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023
  */
trait ManyDetailedStarterSetsAccess 
	extends ManyStarterSetsAccessLike[DetailedStarterSet, ManyDetailedStarterSetsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * set ids of the accessible starter assignments
	  */
	def assignmentSetIds(implicit connection: Connection) = 
		pullColumn(assignmentModel.setIdColumn).map { v => v.getInt }
	
	/**
	  * poke ids of the accessible starter assignments
	  */
	def assignmentPokeIds(implicit connection: Connection) = 
		pullColumn(assignmentModel.pokeIdColumn).map { v => v.getInt }
	
	/**
	  * placements of the accessible starter assignments
	  */
	def assignmentPlacements(implicit connection: Connection) = 
		pullColumn(assignmentModel.placementColumn).map { v => v.getInt }
	
	/**
	  * Model (factory) used for interacting the starter assignments associated with this detailed starter set
	  */
	protected def assignmentModel = StarterAssignmentModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = DetailedStarterSetFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyDetailedStarterSetsAccess = 
		new ManyDetailedStarterSetsAccess.SubAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the placements of the targeted starter assignments
	  * @param newPlacement A new placement to assign
	  * @return Whether any starter assignment was affected
	  */
	def assignmentPlacements_=(newPlacement: Int)(implicit connection: Connection) = 
		putColumn(assignmentModel.placementColumn, newPlacement)
	
	/**
	  * Updates the poke ids of the targeted starter assignments
	  * @param newPokeId A new poke id to assign
	  * @return Whether any starter assignment was affected
	  */
	def assignmentPokeIds_=(newPokeId: Int)(implicit connection: Connection) = 
		putColumn(assignmentModel.pokeIdColumn, newPokeId)
	
	/**
	  * Updates the set ids of the targeted starter assignments
	  * @param newSetId A new set id to assign
	  * @return Whether any starter assignment was affected
	  */
	def assignmentSetIds_=(newSetId: Int)(implicit connection: Connection) = 
		putColumn(assignmentModel.setIdColumn, newSetId)
}

