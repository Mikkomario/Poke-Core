package vf.poke.companion.database.access.single.gameplay.poke_training

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.companion.model.stored.gameplay.PokeTraining

/**
  * An access point to individual poke trainings, based on their id
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class DbSinglePokeTraining(id: Int) 
	extends UniquePokeTrainingAccess with SingleIntIdModelAccess[PokeTraining]

