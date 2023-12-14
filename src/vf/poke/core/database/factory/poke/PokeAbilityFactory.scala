package vf.poke.core.database.factory.poke

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import vf.poke.core.database.PokeTables
import vf.poke.core.model.partial.poke.PokeAbilityData
import vf.poke.core.model.stored.poke.PokeAbility

/**
  * Used for reading poke ability data from the DB
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object PokeAbilityFactory extends FromValidatedRowModelFactory[PokeAbility]
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def table = PokeTables.pokeAbility
	
	override protected def fromValidatedModel(valid: Model) = 
		PokeAbility(valid("id").getInt, PokeAbilityData(valid("pokeId").getInt, valid("abilityId").getInt, 
			valid("isHidden").getBoolean))
}

