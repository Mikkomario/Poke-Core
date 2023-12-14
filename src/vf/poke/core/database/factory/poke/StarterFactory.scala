package vf.poke.core.database.factory.poke

import utopia.vault.nosql.factory.row.linked.CombiningFactory
import vf.poke.core.database.factory.randomization.StarterAssignmentFactory
import vf.poke.core.model.combined.poke.Starter
import vf.poke.core.model.stored.poke.Poke
import vf.poke.core.model.stored.randomization.StarterAssignment

/**
  * Used for reading starters from the database
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object StarterFactory extends CombiningFactory[Starter, Poke, StarterAssignment]
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = StarterAssignmentFactory
	
	override def parentFactory = PokeFactory
	
	override def apply(poke: Poke, starterAssignment: StarterAssignment) = Starter(poke, starterAssignment)
}

