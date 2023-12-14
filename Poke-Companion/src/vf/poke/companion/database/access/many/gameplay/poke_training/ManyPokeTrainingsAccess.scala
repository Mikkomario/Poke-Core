package vf.poke.companion.database.access.many.gameplay.poke_training

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import utopia.vault.sql.Condition
import vf.poke.companion.database.factory.gameplay.PokeTrainingFactory
import vf.poke.companion.database.model.gameplay.PokeTrainingModel
import vf.poke.companion.model.stored.gameplay.PokeTraining

import java.time.Instant

object ManyPokeTrainingsAccess
{
	// NESTED	--------------------
	
	private class ManyPokeTrainingsSubView(condition: Condition) extends ManyPokeTrainingsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple poke trainings at a time
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
trait ManyPokeTrainingsAccess 
	extends ManyRowModelAccess[PokeTraining] with NullDeprecatableView[ManyPokeTrainingsAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * run ids of the accessible poke trainings
	  */
	def runIds(implicit connection: Connection) = pullColumn(model.runIdColumn).map { v => v.getInt }
	
	/**
	  * poke ids of the accessible poke trainings
	  */
	def pokeIds(implicit connection: Connection) = pullColumn(model.pokeIdColumn).map { v => v.getInt }
	
	/**
	  * levels of the accessible poke trainings
	  */
	def levels(implicit connection: Connection) = pullColumn(model.levelColumn).map { v => v.getInt }
	
	/**
	  * creation times of the accessible poke trainings
	  */
	def creationTimes(implicit connection: Connection) = pullColumn(model.createdColumn)
		.map { v => v.getInstant }
	
	/**
	  * deprecation times of the accessible poke trainings
	  */
	def deprecationTimes(implicit connection: Connection) = 
		pullColumn(model.deprecatedAfterColumn).flatMap { v => v.instant }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = PokeTrainingModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PokeTrainingFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyPokeTrainingsAccess = 
		new ManyPokeTrainingsAccess.ManyPokeTrainingsSubView(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the creation times of the targeted poke trainings
	  * @param newCreated A new created to assign
	  * @return Whether any poke training was affected
	  */
	def creationTimes_=(newCreated: Instant)(implicit connection: Connection) = 
		putColumn(model.createdColumn, newCreated)
	
	/**
	  * Updates the deprecation times of the targeted poke trainings
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any poke training was affected
	  */
	def deprecationTimes_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfterColumn, newDeprecatedAfter)
	
	/**
	  * Updates the levels of the targeted poke trainings
	  * @param newLevel A new level to assign
	  * @return Whether any poke training was affected
	  */
	def levels_=(newLevel: Int)(implicit connection: Connection) = putColumn(model.levelColumn, newLevel)
	
	/**
	  * Updates the poke ids of the targeted poke trainings
	  * @param newPokeId A new poke id to assign
	  * @return Whether any poke training was affected
	  */
	def pokeIds_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
	
	/**
	  * Updates the run ids of the targeted poke trainings
	  * @param newRunId A new run id to assign
	  * @return Whether any poke training was affected
	  */
	def runIds_=(newRunId: Int)(implicit connection: Connection) = putColumn(model.runIdColumn, newRunId)
}

