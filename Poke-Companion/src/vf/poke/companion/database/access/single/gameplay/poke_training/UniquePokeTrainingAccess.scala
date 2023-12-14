package vf.poke.companion.database.access.single.gameplay.poke_training

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import utopia.vault.sql.Condition
import vf.poke.companion.database.factory.gameplay.PokeTrainingFactory
import vf.poke.companion.database.model.gameplay.PokeTrainingModel
import vf.poke.companion.model.stored.gameplay.PokeTraining

import java.time.Instant

object UniquePokeTrainingAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniquePokeTrainingAccess = new _UniquePokeTrainingAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniquePokeTrainingAccess(condition: Condition) extends UniquePokeTrainingAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct poke trainings.
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
trait UniquePokeTrainingAccess 
	extends SingleRowModelAccess[PokeTraining] with NullDeprecatableView[UniquePokeTrainingAccess] 
		with DistinctModelAccess[PokeTraining, Option[PokeTraining], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the run on which this event occurred. None if no poke training (or value) was found.
	  */
	def runId(implicit connection: Connection) = pullColumn(model.runIdColumn).int
	
	/**
	  * Id of the trained poke. None if no poke training (or value) was found.
	  */
	def pokeId(implicit connection: Connection) = pullColumn(model.pokeIdColumn).int
	
	/**
	  * Reached level. None if no poke training (or value) was found.
	  */
	def level(implicit connection: Connection) = pullColumn(model.levelColumn).int
	
	/**
	  * Time when this poke training was added to the database. None if no poke training (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.createdColumn).instant
	
	/**
	  * Time when this event was replaced 
		with a more recent recording. None if no poke training (or value) was found.
	  */
	def deprecatedAfter(implicit connection: Connection) = pullColumn(model.deprecatedAfterColumn).instant
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = PokeTrainingModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PokeTrainingFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniquePokeTrainingAccess = 
		new UniquePokeTrainingAccess._UniquePokeTrainingAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the creation times of the targeted poke trainings
	  * @param newCreated A new created to assign
	  * @return Whether any poke training was affected
	  */
	def created_=(newCreated: Instant)(implicit connection: Connection) = 
		putColumn(model.createdColumn, newCreated)
	
	/**
	  * Updates the deprecation times of the targeted poke trainings
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any poke training was affected
	  */
	def deprecatedAfter_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfterColumn, newDeprecatedAfter)
	
	/**
	  * Updates the levels of the targeted poke trainings
	  * @param newLevel A new level to assign
	  * @return Whether any poke training was affected
	  */
	def level_=(newLevel: Int)(implicit connection: Connection) = putColumn(model.levelColumn, newLevel)
	
	/**
	  * Updates the poke ids of the targeted poke trainings
	  * @param newPokeId A new poke id to assign
	  * @return Whether any poke training was affected
	  */
	def pokeId_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
	
	/**
	  * Updates the run ids of the targeted poke trainings
	  * @param newRunId A new run id to assign
	  * @return Whether any poke training was affected
	  */
	def runId_=(newRunId: Int)(implicit connection: Connection) = putColumn(model.runIdColumn, newRunId)
}

