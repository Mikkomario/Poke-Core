package vf.poke.core.database.factory.poke

import utopia.flow.generic.model.template.{ModelLike, Property}
import utopia.vault.nosql.factory.row.model.FromRowModelFactory
import vf.poke.core.database.PokeTables
import vf.poke.core.model.enumeration.PokeType
import vf.poke.core.model.partial.poke.PokeData
import vf.poke.core.model.stored.poke.Poke

/**
  * Used for reading poke data from the DB
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object PokeFactory extends FromRowModelFactory[Poke]
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def table = PokeTables.poke
	
	override def apply(model: ModelLike[Property]) = {
		table.validate(model).flatMap { valid => 
			PokeType.fromValue(valid("primaryTypeId")).map { primaryType => 
				Poke(valid("id").getInt, PokeData(valid("randomizationId").getInt, valid("number").getInt, 
					valid("formeIndex").getInt, valid("name").getString, primaryType, 
					valid("secondaryTypeId").int.flatMap(PokeType.findForId)))
			}
		}
	}
}

