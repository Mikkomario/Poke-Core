package vf.poke.core.database.access.single.poke

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.combined.poke.Starter

/**
  * An access point to individual starters, based on their poke id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DbSingleStarter(id: Int) extends UniqueStarterAccess with SingleIntIdModelAccess[Starter]

