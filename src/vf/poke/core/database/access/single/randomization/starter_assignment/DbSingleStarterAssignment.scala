package vf.poke.core.database.access.single.randomization.starter_assignment

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.stored.randomization.StarterAssignment

/**
  * An access point to individual starter assignments, based on their id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DbSingleStarterAssignment(id: Int) 
	extends UniqueStarterAssignmentAccess with SingleIntIdModelAccess[StarterAssignment]

