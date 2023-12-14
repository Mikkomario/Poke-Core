package vf.poke.companion.model.stored.gameplay

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.companion.database.access.single.gameplay.attack_experiment.DbSingleAttackExperiment
import vf.poke.companion.model.partial.gameplay.AttackExperimentData

/**
  * Represents a attack experiment that has already been stored in the database
  * @param id id of this attack experiment in the database
  * @param data Wrapped attack experiment data
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class AttackExperiment(id: Int, data: AttackExperimentData) 
	extends StoredModelConvertible[AttackExperimentData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this attack experiment in the database
	  */
	def access = DbSingleAttackExperiment(id)
}

