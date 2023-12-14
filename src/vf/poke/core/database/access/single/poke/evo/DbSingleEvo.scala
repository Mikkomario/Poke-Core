package vf.poke.core.database.access.single.poke.evo

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.stored.poke.Evo

/**
  * An access point to individual evos, based on their id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DbSingleEvo(id: Int) extends UniqueEvoAccess with SingleIntIdModelAccess[Evo]

