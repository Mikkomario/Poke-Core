package vf.poke.companion.database.factory.gameplay

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import vf.poke.companion.database.PokeCompanionTables
import vf.poke.companion.database.model.gameplay.PokeTrainingModel
import vf.poke.companion.model.partial.gameplay.PokeTrainingData
import vf.poke.companion.model.stored.gameplay.PokeTraining

/**
  * Used for reading poke training data from the DB
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object PokeTrainingFactory extends FromValidatedRowModelFactory[PokeTraining] with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def nonDeprecatedCondition = PokeTrainingModel.nonDeprecatedCondition
	
	override def table = PokeCompanionTables.pokeTraining
	
	override protected def fromValidatedModel(valid: Model) = 
		PokeTraining(valid("id").getInt, PokeTrainingData(valid("runId").getInt, valid("pokeId").getInt, 
			valid("level").getInt, valid("created").getInstant, valid("deprecatedAfter").instant))
}

