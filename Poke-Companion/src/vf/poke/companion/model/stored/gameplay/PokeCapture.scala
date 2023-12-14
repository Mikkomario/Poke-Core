package vf.poke.companion.model.stored.gameplay

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.companion.database.access.single.gameplay.poke_capture.DbSinglePokeCapture
import vf.poke.companion.model.partial.gameplay.PokeCaptureData

/**
  * Represents a poke capture that has already been stored in the database
  * @param id id of this poke capture in the database
  * @param data Wrapped poke capture data
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class PokeCapture(id: Int, data: PokeCaptureData) extends StoredModelConvertible[PokeCaptureData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this poke capture in the database
	  */
	def access = DbSinglePokeCapture(id)
}

