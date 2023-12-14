package vf.poke.companion.model.stored.gameplay

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.companion.database.access.single.gameplay.poke_training.DbSinglePokeTraining
import vf.poke.companion.model.partial.gameplay.PokeTrainingData

/**
  * Represents a poke training that has already been stored in the database
  * @param id id of this poke training in the database
  * @param data Wrapped poke training data
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class PokeTraining(id: Int, data: PokeTrainingData) extends StoredModelConvertible[PokeTrainingData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this poke training in the database
	  */
	def access = DbSinglePokeTraining(id)
}

