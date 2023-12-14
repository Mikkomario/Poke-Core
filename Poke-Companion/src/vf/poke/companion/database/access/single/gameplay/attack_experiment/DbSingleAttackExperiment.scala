package vf.poke.companion.database.access.single.gameplay.attack_experiment

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.companion.model.stored.gameplay.AttackExperiment

/**
  * An access point to individual attack experiments, based on their id
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class DbSingleAttackExperiment(id: Int) 
	extends UniqueAttackExperimentAccess with SingleIntIdModelAccess[AttackExperiment]

