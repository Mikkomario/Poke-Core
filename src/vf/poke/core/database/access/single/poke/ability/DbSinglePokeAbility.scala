package vf.poke.core.database.access.single.poke.ability

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.stored.poke.PokeAbility

/**
  * An access point to individual poke abilities, based on their id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DbSinglePokeAbility(id: Int) 
	extends UniquePokeAbilityAccess with SingleIntIdModelAccess[PokeAbility]

