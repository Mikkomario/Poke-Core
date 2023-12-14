package vf.poke.core.database.access.single.randomization.move

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.combined.randomization.LearntMove

/**
  * An access point to individual learnt moves, based on their move id
  * @author Mikko Hilpinen
  * @since 13.12.2023, v1.0
  */
case class DbSingleLearntMove(id: Int) extends UniqueLearntMoveAccess with SingleIntIdModelAccess[LearntMove]

