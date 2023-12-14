package vf.poke.companion.database.model.gameplay

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.companion.database.factory.gameplay.AttackExperimentFactory
import vf.poke.companion.model.partial.gameplay.AttackExperimentData
import vf.poke.companion.model.stored.gameplay.AttackExperiment
import vf.poke.core.model.enumeration.PokeType

import java.time.Instant

/**
  * Used for constructing AttackExperimentModel instances and for inserting attack experiments to the database
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object AttackExperimentModel 
	extends DataInserter[AttackExperimentModel, AttackExperiment, AttackExperimentData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains attack experiment run id
	  */
	val runIdAttName = "runId"
	
	/**
	  * Name of the property that contains attack experiment opponent id
	  */
	val opponentIdAttName = "opponentId"
	
	/**
	  * Name of the property that contains attack experiment attack type
	  */
	val attackTypeAttName = "attackTypeId"
	
	/**
	  * Name of the property that contains attack experiment created
	  */
	val createdAttName = "created"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains attack experiment run id
	  */
	def runIdColumn = table(runIdAttName)
	
	/**
	  * Column that contains attack experiment opponent id
	  */
	def opponentIdColumn = table(opponentIdAttName)
	
	/**
	  * Column that contains attack experiment attack type
	  */
	def attackTypeColumn = table(attackTypeAttName)
	
	/**
	  * Column that contains attack experiment created
	  */
	def createdColumn = table(createdAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = AttackExperimentFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: AttackExperimentData) = 
		apply(None, Some(data.runId), Some(data.opponentId), Some(data.attackType.id), Some(data.created))
	
	override protected def complete(id: Value, data: AttackExperimentData) = AttackExperiment(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param attackType Type of attack used against the poke
	  * @return A model containing only the specified attack type
	  */
	def withAttackType(attackType: PokeType) = apply(attackType = Some(attackType.id))
	
	/**
	  * @param created Time when this attack experiment was added to the database
	  * @return A model containing only the specified created
	  */
	def withCreated(created: Instant) = apply(created = Some(created))
	
	/**
	  * @param id A attack experiment id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param opponentId Id of the opposing poke
	  * @return A model containing only the specified opponent id
	  */
	def withOpponentId(opponentId: Int) = apply(opponentId = Some(opponentId))
	
	/**
	  * @param runId Id of the game run on which this attempt was made
	  * @return A model containing only the specified run id
	  */
	def withRunId(runId: Int) = apply(runId = Some(runId))
}

/**
  * Used for interacting with AttackExperiments in the database
  * @param id attack experiment database id
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class AttackExperimentModel(id: Option[Int] = None, runId: Option[Int] = None, 
	opponentId: Option[Int] = None, attackType: Option[Int] = None, created: Option[Instant] = None) 
	extends StorableWithFactory[AttackExperiment]
{
	// IMPLEMENTED	--------------------
	
	override def factory = AttackExperimentModel.factory
	
	override def valueProperties = {
		import AttackExperimentModel._
		Vector("id" -> id, runIdAttName -> runId, opponentIdAttName -> opponentId, 
			attackTypeAttName -> attackType, createdAttName -> created)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param attackType Type of attack used against the poke
	  * @return A new copy of this model with the specified attack type
	  */
	def withAttackType(attackType: PokeType) = copy(attackType = Some(attackType.id))
	
	/**
	  * @param created Time when this attack experiment was added to the database
	  * @return A new copy of this model with the specified created
	  */
	def withCreated(created: Instant) = copy(created = Some(created))
	
	/**
	  * @param opponentId Id of the opposing poke
	  * @return A new copy of this model with the specified opponent id
	  */
	def withOpponentId(opponentId: Int) = copy(opponentId = Some(opponentId))
	
	/**
	  * @param runId Id of the game run on which this attempt was made
	  * @return A new copy of this model with the specified run id
	  */
	def withRunId(runId: Int) = copy(runId = Some(runId))
}

