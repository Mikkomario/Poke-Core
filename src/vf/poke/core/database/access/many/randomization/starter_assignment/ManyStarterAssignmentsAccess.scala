package vf.poke.core.database.access.many.randomization.starter_assignment

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.StarterAssignmentFactory
import vf.poke.core.database.model.randomization.StarterAssignmentModel
import vf.poke.core.model.stored.randomization.StarterAssignment

object ManyStarterAssignmentsAccess
{
	// NESTED	--------------------
	
	private class ManyStarterAssignmentsSubView(condition: Condition) extends ManyStarterAssignmentsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple starter assignments at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait ManyStarterAssignmentsAccess 
	extends ManyRowModelAccess[StarterAssignment] with FilterableView[ManyStarterAssignmentsAccess] 
		with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * set ids of the accessible starter assignments
	  */
	def setIds(implicit connection: Connection) = pullColumn(model.setIdColumn).map { v => v.getInt }
	
	/**
	  * poke ids of the accessible starter assignments
	  */
	def pokeIds(implicit connection: Connection) = pullColumn(model.pokeIdColumn).map { v => v.getInt }
	
	/**
	  * placements of the accessible starter assignments
	  */
	def placements(implicit connection: Connection) = pullColumn(model.placementColumn).map { v => v.getInt }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = StarterAssignmentModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StarterAssignmentFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyStarterAssignmentsAccess = 
		new ManyStarterAssignmentsAccess.ManyStarterAssignmentsSubView(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the placements of the targeted starter assignments
	  * @param newPlacement A new placement to assign
	  * @return Whether any starter assignment was affected
	  */
	def placements_=(newPlacement: Int)(implicit connection: Connection) = 
		putColumn(model.placementColumn, newPlacement)
	
	/**
	  * Updates the poke ids of the targeted starter assignments
	  * @param newPokeId A new poke id to assign
	  * @return Whether any starter assignment was affected
	  */
	def pokeIds_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
	
	/**
	  * Updates the set ids of the targeted starter assignments
	  * @param newSetId A new set id to assign
	  * @return Whether any starter assignment was affected
	  */
	def setIds_=(newSetId: Int)(implicit connection: Connection) = putColumn(model.setIdColumn, newSetId)
}

