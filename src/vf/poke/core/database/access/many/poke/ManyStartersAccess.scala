package vf.poke.core.database.access.many.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.StarterFactory
import vf.poke.core.database.model.randomization.StarterAssignmentModel
import vf.poke.core.model.combined.poke.Starter

object ManyStartersAccess
{
	// NESTED	--------------------
	
	private class SubAccess(condition: Condition) extends ManyStartersAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return multiple starters at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023
  */
trait ManyStartersAccess 
	extends ManyPokesAccessLike[Starter, ManyStartersAccess] with ManyRowModelAccess[Starter]
{
	// COMPUTED	--------------------
	
	/**
	  * set ids of the accessible starter assignments
	  */
	def starterAssignmentSetIds(implicit connection: Connection) = 
		pullColumn(starterAssignmentModel.setIdColumn).map { v => v.getInt }
	
	/**
	  * poke ids of the accessible starter assignments
	  */
	def starterAssignmentPokeIds(implicit connection: Connection) = 
		pullColumn(starterAssignmentModel.pokeIdColumn).map { v => v.getInt }
	
	/**
	  * placements of the accessible starter assignments
	  */
	def starterAssignmentPlacements(implicit connection: Connection) = 
		pullColumn(starterAssignmentModel.placementColumn).map { v => v.getInt }
	
	/**
	  * Model (factory) used for interacting the starter assignments associated with this starter
	  */
	protected def starterAssignmentModel = StarterAssignmentModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StarterFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyStartersAccess = 
		new ManyStartersAccess.SubAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the placements of the targeted starter assignments
	  * @param newPlacement A new placement to assign
	  * @return Whether any starter assignment was affected
	  */
	def starterAssignmentPlacements_=(newPlacement: Int)(implicit connection: Connection) = 
		putColumn(starterAssignmentModel.placementColumn, newPlacement)
	
	/**
	  * Updates the poke ids of the targeted starter assignments
	  * @param newPokeId A new poke id to assign
	  * @return Whether any starter assignment was affected
	  */
	def starterAssignmentPokeIds_=(newPokeId: Int)(implicit connection: Connection) = 
		putColumn(starterAssignmentModel.pokeIdColumn, newPokeId)
	
	/**
	  * Updates the set ids of the targeted starter assignments
	  * @param newSetId A new set id to assign
	  * @return Whether any starter assignment was affected
	  */
	def starterAssignmentSetIds_=(newSetId: Int)(implicit connection: Connection) = 
		putColumn(starterAssignmentModel.setIdColumn, newSetId)
}

