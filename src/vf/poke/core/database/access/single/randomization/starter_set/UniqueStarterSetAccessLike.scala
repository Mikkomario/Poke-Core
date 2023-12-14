package vf.poke.core.database.access.single.randomization.starter_set

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import vf.poke.core.database.model.randomization.StarterSetModel

/**
  * A common trait for access points which target individual starter sets or similar items at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniqueStarterSetAccessLike[+A] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the randomization where these starters appear. None if no starter set (or value) was found.
	  */
	def randomizationId(implicit connection: Connection) = pullColumn(model.randomizationIdColumn).int
	
	/**
	  * Relative set index within the game, 0-based. None if no starter set (or value) was found.
	  */
	def placement(implicit connection: Connection) = pullColumn(model.placementColumn).int
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = StarterSetModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the placements of the targeted starter sets
	  * @param newPlacement A new placement to assign
	  * @return Whether any starter set was affected
	  */
	def placement_=(newPlacement: Int)(implicit connection: Connection) = 
		putColumn(model.placementColumn, newPlacement)
	
	/**
	  * Updates the randomization ids of the targeted starter sets
	  * @param newRandomizationId A new randomization id to assign
	  * @return Whether any starter set was affected
	  */
	def randomizationId_=(newRandomizationId: Int)(implicit connection: Connection) = 
		putColumn(model.randomizationIdColumn, newRandomizationId)
}

