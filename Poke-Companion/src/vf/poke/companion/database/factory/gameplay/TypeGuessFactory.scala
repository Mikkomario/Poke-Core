package vf.poke.companion.database.factory.gameplay

import utopia.flow.generic.model.template.{ModelLike, Property}
import utopia.vault.nosql.factory.row.model.FromRowModelFactory
import utopia.vault.nosql.template.Deprecatable
import vf.poke.companion.database.PokeCompanionTables
import vf.poke.companion.database.model.gameplay.TypeGuessModel
import vf.poke.companion.model.partial.gameplay.TypeGuessData
import vf.poke.companion.model.stored.gameplay.TypeGuess
import vf.poke.core.model.enumeration.PokeType

/**
  * Used for reading type guess data from the DB
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object TypeGuessFactory extends FromRowModelFactory[TypeGuess] with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def nonDeprecatedCondition = TypeGuessModel.nonDeprecatedCondition
	
	override def table = PokeCompanionTables.typeGuess
	
	override def apply(model: ModelLike[Property]) = {
		table.validate(model).flatMap { valid => 
			PokeType.fromValue(valid("guessedTypeId")).map { guessedType => 
				TypeGuess(valid("id").getInt, TypeGuessData(valid("runId").getInt, valid("pokeId").getInt, 
					guessedType, valid("created").getInstant, valid("deprecatedAfter").instant))
			}
		}
	}
}

