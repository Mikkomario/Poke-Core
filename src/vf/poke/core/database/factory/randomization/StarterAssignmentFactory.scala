package vf.poke.core.database.factory.randomization

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import vf.poke.core.database.PokeTables
import vf.poke.core.model.partial.randomization.StarterAssignmentData
import vf.poke.core.model.stored.randomization.StarterAssignment

/**
  * Used for reading starter assignment data from the DB
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object StarterAssignmentFactory extends FromValidatedRowModelFactory[StarterAssignment]
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def table = PokeTables.starterAssignment
	
	override protected def fromValidatedModel(valid: Model) = 
		StarterAssignment(valid("id").getInt, StarterAssignmentData(valid("setId").getInt, 
			valid("pokeId").getInt, valid("placement").getInt))
}

