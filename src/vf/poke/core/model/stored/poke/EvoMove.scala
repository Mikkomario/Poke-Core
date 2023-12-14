package vf.poke.core.model.stored.poke

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.core.database.access.single.poke.evo_move.DbSingleEvoMove
import vf.poke.core.model.partial.poke.EvoMoveData

/**
  * Represents a evo move that has already been stored in the database
  * @param id id of this evo move in the database
  * @param data Wrapped evo move data
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class EvoMove(id: Int, data: EvoMoveData) extends StoredModelConvertible[EvoMoveData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this evo move in the database
	  */
	def access = DbSingleEvoMove(id)
}

