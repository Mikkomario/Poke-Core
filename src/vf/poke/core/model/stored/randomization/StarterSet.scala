package vf.poke.core.model.stored.randomization

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.core.database.access.single.randomization.starter_set.DbSingleStarterSet
import vf.poke.core.model.partial.randomization.StarterSetData

/**
  * Represents a starter set that has already been stored in the database
  * @param id id of this starter set in the database
  * @param data Wrapped starter set data
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class StarterSet(id: Int, data: StarterSetData) extends StoredModelConvertible[StarterSetData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this starter set in the database
	  */
	def access = DbSingleStarterSet(id)
}

