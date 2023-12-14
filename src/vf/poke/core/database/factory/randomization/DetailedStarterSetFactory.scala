package vf.poke.core.database.factory.randomization

import utopia.vault.nosql.factory.multi.MultiCombiningFactory
import vf.poke.core.model.combined.randomization.DetailedStarterSet
import vf.poke.core.model.stored.randomization.{StarterAssignment, StarterSet}

/**
  * Used for reading detailed starter sets from the database
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DetailedStarterSetFactory 
	extends MultiCombiningFactory[DetailedStarterSet, StarterSet, StarterAssignment]
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = StarterAssignmentFactory
	
	override def isAlwaysLinked = true
	
	override def parentFactory = StarterSetFactory
	
	override def apply(set: StarterSet, assignments: Vector[StarterAssignment]) = 
		DetailedStarterSet(set, assignments)
}

