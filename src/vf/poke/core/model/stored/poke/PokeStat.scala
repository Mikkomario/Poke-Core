package vf.poke.core.model.stored.poke

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.core.database.access.single.poke.stat.DbSinglePokeStat
import vf.poke.core.model.partial.poke.PokeStatData

/**
  * Represents a poke stat that has already been stored in the database
  * @param id id of this poke stat in the database
  * @param data Wrapped poke stat data
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class PokeStat(id: Int, data: PokeStatData) extends StoredModelConvertible[PokeStatData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this poke stat in the database
	  */
	def access = DbSinglePokeStat(id)
}

