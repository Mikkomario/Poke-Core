package vf.poke.companion.database.access.single.gameplay.run

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.companion.model.stored.gameplay.Run

/**
  * An access point to individual runs, based on their id
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class DbSingleRun(id: Int) extends UniqueRunAccess with SingleIntIdModelAccess[Run]

