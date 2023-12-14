package vf.poke.core.database.factory.randomization

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import vf.poke.core.database.PokeTables
import vf.poke.core.model.partial.randomization.StarterSetData
import vf.poke.core.model.stored.randomization.StarterSet

/**
  * Used for reading starter set data from the DB
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object StarterSetFactory extends FromValidatedRowModelFactory[StarterSet]
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def table = PokeTables.starterSet
	
	override protected def fromValidatedModel(valid: Model) = 
		StarterSet(valid("id").getInt, StarterSetData(valid("randomizationId").getInt, 
			valid("placement").getInt))
}

