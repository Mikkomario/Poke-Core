package vf.poke.companion.database.access.single.gameplay.attack_experiment

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.companion.controller.app.PokeRunEnvironment
import vf.poke.companion.database.factory.gameplay.AttackExperimentFactory
import vf.poke.companion.database.model.gameplay.AttackExperimentModel
import vf.poke.companion.model.stored.gameplay.AttackExperiment
import vf.poke.core.model.enumeration.PokeType

/**
  * Used for accessing individual attack experiments
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object DbAttackExperiment extends SingleRowModelAccess[AttackExperiment] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = AttackExperimentModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = AttackExperimentFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted attack experiment
	  * @return An access point to that attack experiment
	  */
	def apply(id: Int) = DbSingleAttackExperiment(id)
	
	/**
	 * @param attackType Type of attack
	 * @param opponentPokeId Id of the opposing poke
	 * @param env Implicit poke run env
	 * @return Access to that attack experiment
	 */
	def apply(attackType: PokeType, opponentPokeId: Int)(implicit env: PokeRunEnvironment) =
		filterDistinct(model.withRunId(env.run.id).withAttackType(attackType).withOpponentId(opponentPokeId).toCondition)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique attack experiments.
	  * @return An access point to the attack experiment that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniqueAttackExperimentAccess(mergeCondition(condition))
}

