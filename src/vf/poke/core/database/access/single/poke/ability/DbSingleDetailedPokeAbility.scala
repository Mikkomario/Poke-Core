package vf.poke.core.database.access.single.poke.ability

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.model.combined.poke.DetailedPokeAbility

/**
  * An access point to individual detailed poke abilities, based on their poke ability id
  * @author Mikko Hilpinen
  * @since 13.12.2023, v1.0
  */
case class DbSingleDetailedPokeAbility(id: Int) 
	extends UniqueDetailedPokeAbilityAccess with SingleIntIdModelAccess[DetailedPokeAbility]

