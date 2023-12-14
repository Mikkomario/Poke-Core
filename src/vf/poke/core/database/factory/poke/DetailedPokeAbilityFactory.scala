package vf.poke.core.database.factory.poke

import utopia.vault.nosql.factory.row.linked.CombiningFactory
import vf.poke.core.database.factory.game.AbilityFactory
import vf.poke.core.model.combined.poke.DetailedPokeAbility
import vf.poke.core.model.stored.game.Ability
import vf.poke.core.model.stored.poke.PokeAbility

/**
  * Used for reading detailed poke abilities from the database
  * @author Mikko Hilpinen
  * @since 13.12.2023, v1.0
  */
object DetailedPokeAbilityFactory extends CombiningFactory[DetailedPokeAbility, PokeAbility, Ability]
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = AbilityFactory
	
	override def parentFactory = PokeAbilityFactory
	
	override def apply(pokeAbility: PokeAbility, ability: Ability) = DetailedPokeAbility(pokeAbility, ability)
}

