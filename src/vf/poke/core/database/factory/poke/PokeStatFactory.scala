package vf.poke.core.database.factory.poke

import utopia.flow.generic.model.template.{ModelLike, Property}
import utopia.vault.nosql.factory.row.model.FromRowModelFactory
import vf.poke.core.database.PokeTables
import vf.poke.core.model.enumeration.Stat
import vf.poke.core.model.partial.poke.PokeStatData
import vf.poke.core.model.stored.poke.PokeStat

/**
  * Used for reading poke stat data from the DB
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object PokeStatFactory extends FromRowModelFactory[PokeStat]
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def table = PokeTables.pokeStat
	
	override def apply(model: ModelLike[Property]) = {
		table.validate(model).flatMap { valid => 
			Stat.fromValue(valid("statId")).map { stat => 
				PokeStat(valid("id").getInt, PokeStatData(valid("pokeId").getInt, stat, 
					valid("value").getInt))
			}
		}
	}
}

