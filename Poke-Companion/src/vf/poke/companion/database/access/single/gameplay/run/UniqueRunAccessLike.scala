package vf.poke.companion.database.access.single.gameplay.run

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import vf.poke.companion.database.model.gameplay.RunModel

import java.time.Instant

/**
  * A common trait for access points which target individual runs or similar items at a time
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
trait UniqueRunAccessLike[+A] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Name of this run, for example consisting of the player name. None if no run (or value) was found.
	  */
	def name(implicit connection: Connection) = pullColumn(model.nameColumn).getString
	
	/**
	  * Id of the randomization that's applied to this game run. None if no run (or value) was found.
	  */
	def randomizationId(implicit connection: Connection) = pullColumn(model.randomizationIdColumn).int
	
	/**
	  * Time when this run was added to the database. None if no run (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.createdColumn).instant
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = RunModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the creation times of the targeted runs
	  * @param newCreated A new created to assign
	  * @return Whether any run was affected
	  */
	def created_=(newCreated: Instant)(implicit connection: Connection) = 
		putColumn(model.createdColumn, newCreated)
	
	/**
	  * Updates the names of the targeted runs
	  * @param newName A new name to assign
	  * @return Whether any run was affected
	  */
	def name_=(newName: String)(implicit connection: Connection) = putColumn(model.nameColumn, newName)
	
	/**
	  * Updates the randomization ids of the targeted runs
	  * @param newRandomizationId A new randomization id to assign
	  * @return Whether any run was affected
	  */
	def randomizationId_=(newRandomizationId: Int)(implicit connection: Connection) = 
		putColumn(model.randomizationIdColumn, newRandomizationId)
}

