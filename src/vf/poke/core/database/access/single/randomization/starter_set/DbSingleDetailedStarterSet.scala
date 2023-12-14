package vf.poke.core.database.access.single.randomization.starter_set

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.combined.randomization.DetailedStarterSet

/**
  * An access point to individual detailed starter sets, based on their set id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DbSingleDetailedStarterSet(id: Int) 
	extends UniqueDetailedStarterSetAccess with SingleIntIdModelAccess[DetailedStarterSet]

