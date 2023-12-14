package vf.poke.core.database.access.single.randomization.starter_set

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.stored.randomization.StarterSet

/**
  * An access point to individual starter sets, based on their id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DbSingleStarterSet(id: Int) extends UniqueStarterSetAccess with SingleIntIdModelAccess[StarterSet]

