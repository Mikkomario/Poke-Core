package vf.poke.companion.database.factory.gameplay

import utopia.flow.generic.model.template.{ModelLike, Property}
import utopia.vault.nosql.factory.row.model.FromRowModelFactory
import vf.poke.companion.database.PokeCompanionTables
import vf.poke.companion.model.partial.gameplay.AttackExperimentData
import vf.poke.companion.model.stored.gameplay.AttackExperiment
import vf.poke.core.model.enumeration.PokeType

/**
  * Used for reading attack experiment data from the DB
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object AttackExperimentFactory extends FromRowModelFactory[AttackExperiment]
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def table = PokeCompanionTables.attackExperiment
	
	override def apply(model: ModelLike[Property]) = {
		table.validate(model).flatMap { valid => 
			PokeType.fromValue(valid("attackTypeId")).map { attackType => 
				AttackExperiment(valid("id").getInt, AttackExperimentData(valid("runId").getInt, 
					valid("opponentId").getInt, attackType, valid("created").getInstant))
			}
		}
	}
}

