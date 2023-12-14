package vf.poke.companion.model.stored.gameplay

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.companion.database.access.single.gameplay.run.DbSingleRun
import vf.poke.companion.model.partial.gameplay.RunData

/**
  * Represents a run that has already been stored in the database
  * @param id id of this run in the database
  * @param data Wrapped run data
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class Run(id: Int, data: RunData) extends StoredModelConvertible[RunData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this run in the database
	  */
	def access = DbSingleRun(id)
}

