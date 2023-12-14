package vf.poke.core.database.access.single.poke.stat

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.stored.poke.PokeStat

/**
  * An access point to individual poke stats, based on their id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DbSinglePokeStat(id: Int) extends UniquePokeStatAccess with SingleIntIdModelAccess[PokeStat]

