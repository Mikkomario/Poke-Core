package vf.poke.companion.database.access.many.gameplay.run

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.poke.companion.database.model.gameplay.RunModel

import java.time.Instant

/**
  * A common trait for access points which target multiple runs or similar instances at a time
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
trait ManyRunsAccessLike[+A, +Repr] extends ManyModelAccess[A] with Indexed with FilterableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * names of the accessible runs
	  */
	def names(implicit connection: Connection) = pullColumn(model.nameColumn).flatMap { _.string }
	
	/**
	  * randomization ids of the accessible runs
	  */
	def randomizationIds(implicit connection: Connection) = 
		pullColumn(model.randomizationIdColumn).map { v => v.getInt }
	
	/**
	  * creation times of the accessible runs
	  */
	def creationTimes(implicit connection: Connection) = pullColumn(model.createdColumn)
		.map { v => v.getInstant }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = RunModel
	
	
	// OTHER	--------------------
	
	def withName(name: String) = filter(model.withName(name).toCondition)
	
	/**
	  * Updates the creation times of the targeted runs
	  * @param newCreated A new created to assign
	  * @return Whether any run was affected
	  */
	def creationTimes_=(newCreated: Instant)(implicit connection: Connection) = 
		putColumn(model.createdColumn, newCreated)
	
	/**
	  * Updates the names of the targeted runs
	  * @param newName A new name to assign
	  * @return Whether any run was affected
	  */
	def names_=(newName: String)(implicit connection: Connection) = putColumn(model.nameColumn, newName)
	
	/**
	  * Updates the randomization ids of the targeted runs
	  * @param newRandomizationId A new randomization id to assign
	  * @return Whether any run was affected
	  */
	def randomizationIds_=(newRandomizationId: Int)(implicit connection: Connection) = 
		putColumn(model.randomizationIdColumn, newRandomizationId)
}

