package vf.poke.companion.database.access.many.gameplay.attack_experiment

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.companion.controller.app.PokeRunEnvironment
import vf.poke.companion.database.factory.gameplay.AttackExperimentFactory
import vf.poke.companion.database.model.gameplay.AttackExperimentModel
import vf.poke.companion.model.stored.gameplay.AttackExperiment
import vf.poke.core.model.enumeration.PokeType

import java.time.Instant

object ManyAttackExperimentsAccess
{
	// NESTED	--------------------
	
	private class ManyAttackExperimentsSubView(condition: Condition) extends ManyAttackExperimentsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple attack experiments at a time
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
trait ManyAttackExperimentsAccess 
	extends ManyRowModelAccess[AttackExperiment] with FilterableView[ManyAttackExperimentsAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * run ids of the accessible attack experiments
	  */
	def runIds(implicit connection: Connection) = pullColumn(model.runIdColumn).map { v => v.getInt }
	
	/**
	  * opponent ids of the accessible attack experiments
	  */
	def opponentIds(implicit connection: Connection) = pullColumn(model.opponentIdColumn)
		.map { v => v.getInt }
	
	/**
	  * attack types of the accessible attack experiments
	  */
	def attackTypes(implicit connection: Connection) = 
		pullColumn(model.attackTypeColumn).map { v => v.getInt }.flatMap(PokeType.findForId)
	
	/**
	  * creation times of the accessible attack experiments
	  */
	def creationTimes(implicit connection: Connection) = pullColumn(model.createdColumn)
		.map { v => v.getInstant }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = AttackExperimentModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = AttackExperimentFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyAttackExperimentsAccess = 
		new ManyAttackExperimentsAccess.ManyAttackExperimentsSubView(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	 * @param pokeId Id of the opposing poke
	 * @return Access to attack experiments against that poke
	 */
	def against(pokeId: Int)(implicit env: PokeRunEnvironment) =
		filter(model.withRunId(env.run.id).withOpponentId(pokeId).toCondition)
	
	/**
	  * Updates the attack types of the targeted attack experiments
	  * @param newAttackType A new attack type to assign
	  * @return Whether any attack experiment was affected
	  */
	def attackTypes_=(newAttackType: PokeType)(implicit connection: Connection) = 
		putColumn(model.attackTypeColumn, newAttackType.id)
	
	/**
	  * Updates the creation times of the targeted attack experiments
	  * @param newCreated A new created to assign
	  * @return Whether any attack experiment was affected
	  */
	def creationTimes_=(newCreated: Instant)(implicit connection: Connection) = 
		putColumn(model.createdColumn, newCreated)
	
	/**
	  * Updates the opponent ids of the targeted attack experiments
	  * @param newOpponentId A new opponent id to assign
	  * @return Whether any attack experiment was affected
	  */
	def opponentIds_=(newOpponentId: Int)(implicit connection: Connection) = 
		putColumn(model.opponentIdColumn, newOpponentId)
	
	/**
	  * Updates the run ids of the targeted attack experiments
	  * @param newRunId A new run id to assign
	  * @return Whether any attack experiment was affected
	  */
	def runIds_=(newRunId: Int)(implicit connection: Connection) = putColumn(model.runIdColumn, newRunId)
}

