package vf.poke.core.model.stored.poke

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.core.database.access.single.poke.ability.DbSinglePokeAbility
import vf.poke.core.model.partial.poke.PokeAbilityData

/**
  * Represents a poke ability that has already been stored in the database
  * @param id id of this poke ability in the database
  * @param data Wrapped poke ability data
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class PokeAbility(id: Int, data: PokeAbilityData) extends StoredModelConvertible[PokeAbilityData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this poke ability in the database
	  */
	def access = DbSinglePokeAbility(id)
}

