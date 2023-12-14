package vf.poke.core.model.stored.poke

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.core.database.access.single.poke.evo.DbSingleEvo
import vf.poke.core.model.partial.poke.EvoData

/**
  * Represents a evo that has already been stored in the database
  * @param id id of this evo in the database
  * @param data Wrapped evo data
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class Evo(id: Int, data: EvoData) extends StoredModelConvertible[EvoData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this evo in the database
	  */
	def access = DbSingleEvo(id)
}

