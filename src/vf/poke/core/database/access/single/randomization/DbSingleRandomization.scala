package vf.poke.core.database.access.single.randomization

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.stored.randomization.Randomization

/**
  * An access point to individual randomizations, based on their id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DbSingleRandomization(id: Int) 
	extends UniqueRandomizationAccess with SingleIntIdModelAccess[Randomization]