package vf.poke.core.database.factory.game

import utopia.flow.generic.model.immutable.Model
import utopia.vault.nosql.factory.row.model.FromValidatedRowModelFactory
import vf.poke.core.database.PokeTables
import vf.poke.core.model.partial.game.AbilityData
import vf.poke.core.model.stored.game.Ability

/**
  * Used for reading ability data from the DB
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object AbilityFactory extends FromValidatedRowModelFactory[Ability]
{
	// IMPLEMENTED	--------------------
	
	override def defaultOrdering = None
	
	override def table = PokeTables.ability
	
	override protected def fromValidatedModel(valid: Model) = 
		Ability(valid("id").getInt, AbilityData(valid("gameId").getInt, valid("indexInGame").getInt, 
			valid("name").getString))
}

