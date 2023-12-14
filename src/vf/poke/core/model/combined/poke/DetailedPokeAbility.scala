package vf.poke.core.model.combined.poke

import utopia.flow.view.template.Extender
import vf.poke.core.model.partial.poke.PokeAbilityData
import vf.poke.core.model.stored.game.Ability
import vf.poke.core.model.stored.poke.PokeAbility

/**
  * Includes ability information with an ability-assignment
  * @author Mikko Hilpinen
  * @since 13.12.2023, v1.0
  */
case class DetailedPokeAbility(pokeAbility: PokeAbility, ability: Ability) extends Extender[PokeAbilityData]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of this poke ability in the database
	  */
	def id = pokeAbility.id
	
	
	// IMPLEMENTED	--------------------
	
	override def wrapped = pokeAbility.data
}

