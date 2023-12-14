package vf.poke.companion.database.factory.gameplay

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.FromRowFactoryWithTimestamps
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import vf.poke.companion.database.PokeCompanionTables
import vf.poke.companion.model.partial.gameplay.RunData
import vf.poke.companion.model.stored.gameplay.Run

/**
  * Used for reading run data from the DB
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object RunFactory extends FromValidatedRowModelFactory[Run] with FromRowFactoryWithTimestamps[Run]
{
	// IMPLEMENTED	--------------------
	
	override def creationTimePropertyName = "created"
	
	override def table = PokeCompanionTables.run
	
	override protected def fromValidatedModel(valid: Model) = 
		Run(valid("id").getInt, RunData(valid("name").getString, valid("randomizationId").getInt, 
			valid("created").getInstant))
}

