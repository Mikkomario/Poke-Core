package vf.poke.core.database.access.single.game.ability

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.stored.game.Ability

/**
  * An access point to individual abilities, based on their id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DbSingleAbility(id: Int) extends UniqueAbilityAccess with SingleIntIdModelAccess[Ability]

