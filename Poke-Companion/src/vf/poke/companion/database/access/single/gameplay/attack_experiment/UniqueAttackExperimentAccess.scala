package vf.poke.companion.database.access.single.gameplay.attack_experiment

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.companion.database.factory.gameplay.AttackExperimentFactory
import vf.poke.companion.database.model.gameplay.AttackExperimentModel
import vf.poke.companion.model.stored.gameplay.AttackExperiment
import vf.poke.core.model.enumeration.PokeType

import java.time.Instant

object UniqueAttackExperimentAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueAttackExperimentAccess =
		 new _UniqueAttackExperimentAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueAttackExperimentAccess(condition: Condition) extends UniqueAttackExperimentAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct attack experiments.
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
trait UniqueAttackExperimentAccess 
	extends SingleRowModelAccess[AttackExperiment] with FilterableView[UniqueAttackExperimentAccess] 
		with DistinctModelAccess[AttackExperiment, Option[AttackExperiment], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the game run on which this attempt was made. None if no attack experiment (or value) was found.
	  */
	def runId(implicit connection: Connection) = pullColumn(model.runIdColumn).int
	
	/**
	  * Id of the opposing poke. None if no attack experiment (or value) was found.
	  */
	def opponentId(implicit connection: Connection) = pullColumn(model.opponentIdColumn).int
	
	/**
	  * Type of attack used against the poke. None if no attack experiment (or value) was found.
	  */
	def attackType(implicit connection: Connection) = 
		pullColumn(model.attackTypeColumn).int.flatMap(PokeType.findForId)
	
	/**
	  * 
		Time when this attack experiment was added to the database. None if no attack experiment (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.createdColumn).instant
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = AttackExperimentModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = AttackExperimentFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueAttackExperimentAccess = 
		new UniqueAttackExperimentAccess._UniqueAttackExperimentAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the attack types of the targeted attack experiments
	  * @param newAttackType A new attack type to assign
	  * @return Whether any attack experiment was affected
	  */
	def attackType_=(newAttackType: PokeType)(implicit connection: Connection) = 
		putColumn(model.attackTypeColumn, newAttackType.id)
	
	/**
	  * Updates the creation times of the targeted attack experiments
	  * @param newCreated A new created to assign
	  * @return Whether any attack experiment was affected
	  */
	def created_=(newCreated: Instant)(implicit connection: Connection) = 
		putColumn(model.createdColumn, newCreated)
	
	/**
	  * Updates the opponent ids of the targeted attack experiments
	  * @param newOpponentId A new opponent id to assign
	  * @return Whether any attack experiment was affected
	  */
	def opponentId_=(newOpponentId: Int)(implicit connection: Connection) = 
		putColumn(model.opponentIdColumn, newOpponentId)
	
	/**
	  * Updates the run ids of the targeted attack experiments
	  * @param newRunId A new run id to assign
	  * @return Whether any attack experiment was affected
	  */
	def runId_=(newRunId: Int)(implicit connection: Connection) = putColumn(model.runIdColumn, newRunId)
}

