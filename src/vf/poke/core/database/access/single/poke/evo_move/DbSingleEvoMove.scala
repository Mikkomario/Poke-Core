package vf.poke.core.database.access.single.poke.evo_move

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.stored.poke.EvoMove

/**
  * An access point to individual evo moves, based on their id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DbSingleEvoMove(id: Int) extends UniqueEvoMoveAccess with SingleIntIdModelAccess[EvoMove]

