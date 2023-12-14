package vf.poke.core.database.factory.randomization

import utopia.flow.generic.model.template.{ModelLike, Property}
import utopia.vault.nosql.factory.row.model.FromRowModelFactory
import vf.poke.core.database.PokeTables
import vf.poke.core.model.enumeration.{CriticalRate, MoveCategory, PokeType}
import vf.poke.core.model.partial.randomization.MoveData
import vf.poke.core.model.stored.randomization.Move

/**
  * Used for reading move data from the DB
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object MoveFactory extends FromRowModelFactory[Move]
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def table = PokeTables.move
	
	override def apply(model: ModelLike[Property]) = {
		table.validate(model).flatMap { valid => 
			PokeType.fromValue(valid("moveTypeId")).map { moveType => 
				Move(valid("id").getInt, MoveData(valid("randomizationId").getInt, 
					valid("indexInGame").getInt, valid("name").getString, moveType, 
					valid("categoryId").int.flatMap(MoveCategory.findForId), valid("damage").getInt, 
					valid("hitCount").getDouble, valid("hitRatio").getDouble, 
					CriticalRate.fromValue(valid("criticalRateId"))))
			}
		}
	}
}

