package vf.poke.core.database.factory.randomization

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.FromRowFactoryWithTimestamps
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import vf.poke.core.database.PokeTables
import vf.poke.core.model.partial.randomization.RandomizationData
import vf.poke.core.model.stored.randomization.Randomization

/**
  * Used for reading randomization data from the DB
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object RandomizationFactory 
	extends FromValidatedRowModelFactory[Randomization] with FromRowFactoryWithTimestamps[Randomization]
{
	// IMPLEMENTED	--------------------
	
	override def creationTimePropertyName = "created"
	
	override def table = PokeTables.randomization
	
	override protected def fromValidatedModel(valid: Model) = 
		Randomization(valid("id").getInt, RandomizationData(valid("gameId").getInt, 
			valid("created").getInstant, valid("isOriginal").getBoolean))
}

