package vf.poke.core.model.stored.randomization

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.core.database.access.single.randomization.DbSingleRandomization
import vf.poke.core.model.partial.randomization.RandomizationData

/**
  * Represents a randomization that has already been stored in the database
  * @param id id of this randomization in the database
  * @param data Wrapped randomization data
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class Randomization(id: Int, data: RandomizationData) extends StoredModelConvertible[RandomizationData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this randomization in the database
	  */
	def access = DbSingleRandomization(id)
}

