package vf.poke.core.model.stored.poke

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.core.database.access.single.poke.DbSinglePoke
import vf.poke.core.model.partial.poke.PokeData

/**
  * Represents a poke that has already been stored in the database
  * @param id id of this poke in the database
  * @param data Wrapped poke data
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class Poke(id: Int, data: PokeData) extends StoredModelConvertible[PokeData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this poke in the database
	  */
	def access = DbSinglePoke(id)
}

