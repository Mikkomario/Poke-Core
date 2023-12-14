package vf.poke.core.database.access.single.poke

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.stored.poke.Poke

/**
  * An access point to individual pokes, based on their id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DbSinglePoke(id: Int) extends UniquePokeAccess with SingleIntIdModelAccess[Poke]

