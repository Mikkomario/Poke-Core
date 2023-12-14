package vf.poke.core.model.stored.poke

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.core.database.access.single.poke.move_learn.DbSingleMoveLearn
import vf.poke.core.model.partial.poke.MoveLearnData

/**
  * Represents a move learn that has already been stored in the database
  * @param id id of this move learn in the database
  * @param data Wrapped move learn data
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class MoveLearn(id: Int, data: MoveLearnData) extends StoredModelConvertible[MoveLearnData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this move learn in the database
	  */
	def access = DbSingleMoveLearn(id)
}

