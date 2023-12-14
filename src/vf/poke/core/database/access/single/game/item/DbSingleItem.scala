package vf.poke.core.database.access.single.game.item

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.stored.game.Item

/**
  * An access point to individual items, based on their id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DbSingleItem(id: Int) extends UniqueItemAccess with SingleIntIdModelAccess[Item]

