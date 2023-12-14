package vf.poke.core.model.stored.randomization

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.core.database.access.single.randomization.move.DbSingleMove
import vf.poke.core.model.partial.randomization.MoveData

/**
  * Represents a move that has already been stored in the database
  * @param id id of this move in the database
  * @param data Wrapped move data
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class Move(id: Int, data: MoveData) extends StoredModelConvertible[MoveData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this move in the database
	  */
	def access = DbSingleMove(id)
}

