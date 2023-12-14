package vf.poke.core.database.access.many.randomization.starter_set

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.poke.core.database.model.randomization.StarterSetModel

/**
  * A common trait for access points which target multiple starter sets or similar instances at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait ManyStarterSetsAccessLike[+A, +Repr] extends ManyModelAccess[A] with Indexed with FilterableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * randomization ids of the accessible starter sets
	  */
	def randomizationIds(implicit connection: Connection) = 
		pullColumn(model.randomizationIdColumn).map { v => v.getInt }
	
	/**
	  * placements of the accessible starter sets
	  */
	def placements(implicit connection: Connection) = pullColumn(model.placementColumn).map { v => v.getInt }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = StarterSetModel
	
	
	// OTHER	--------------------
	
	/**
	 * @param randomizationId Id of the targeted randomization
	 * @return Access to starter sets in that randomization
	 */
	def inRandomization(randomizationId: Int) = filter(model.withRandomizationId(randomizationId).toCondition)
	
	/**
	  * Updates the placements of the targeted starter sets
	  * @param newPlacement A new placement to assign
	  * @return Whether any starter set was affected
	  */
	def placements_=(newPlacement: Int)(implicit connection: Connection) = 
		putColumn(model.placementColumn, newPlacement)
	
	/**
	  * Updates the randomization ids of the targeted starter sets
	  * @param newRandomizationId A new randomization id to assign
	  * @return Whether any starter set was affected
	  */
	def randomizationIds_=(newRandomizationId: Int)(implicit connection: Connection) = 
		putColumn(model.randomizationIdColumn, newRandomizationId)
}

