package vf.poke.core.database.access.single.randomization.move

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.stored.randomization.Move

/**
  * An access point to individual moves, based on their id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DbSingleMove(id: Int) extends UniqueMoveAccess with SingleIntIdModelAccess[Move]

