package vf.poke.core.model.stored.randomization

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.core.database.access.single.randomization.starter_assignment.DbSingleStarterAssignment
import vf.poke.core.model.partial.randomization.StarterAssignmentData

/**
  * Represents a starter assignment that has already been stored in the database
  * @param id id of this starter assignment in the database
  * @param data Wrapped starter assignment data
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class StarterAssignment(id: Int, data: StarterAssignmentData) 
	extends StoredModelConvertible[StarterAssignmentData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this starter assignment in the database
	  */
	def access = DbSingleStarterAssignment(id)
}

