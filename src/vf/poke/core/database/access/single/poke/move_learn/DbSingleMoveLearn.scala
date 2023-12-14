package vf.poke.core.database.access.single.poke.move_learn

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.stored.poke.MoveLearn

/**
  * An access point to individual move learns, based on their id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DbSingleMoveLearn(id: Int) extends UniqueMoveLearnAccess with SingleIntIdModelAccess[MoveLearn]

