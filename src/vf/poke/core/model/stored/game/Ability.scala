package vf.poke.core.model.stored.game

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.core.database.access.single.game.ability.DbSingleAbility
import vf.poke.core.model.partial.game.AbilityData

/**
  * Represents a ability that has already been stored in the database
  * @param id id of this ability in the database
  * @param data Wrapped ability data
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class Ability(id: Int, data: AbilityData) extends StoredModelConvertible[AbilityData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this ability in the database
	  */
	def access = DbSingleAbility(id)
}

