package vf.poke.companion.database.model.gameplay

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import utopia.vault.nosql.storable.deprecation.DeprecatableAfter
import vf.poke.companion.database.factory.gameplay.PokeTrainingFactory
import vf.poke.companion.model.partial.gameplay.PokeTrainingData
import vf.poke.companion.model.stored.gameplay.PokeTraining

import java.time.Instant

/**
  * Used for constructing PokeTrainingModel instances and for inserting poke trainings to the database
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object PokeTrainingModel 
	extends DataInserter[PokeTrainingModel, PokeTraining, PokeTrainingData] 
		with DeprecatableAfter[PokeTrainingModel]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains poke training run id
	  */
	val runIdAttName = "runId"
	
	/**
	  * Name of the property that contains poke training poke id
	  */
	val pokeIdAttName = "pokeId"
	
	/**
	  * Name of the property that contains poke training level
	  */
	val levelAttName = "level"
	
	/**
	  * Name of the property that contains poke training created
	  */
	val createdAttName = "created"
	
	/**
	  * Name of the property that contains poke training deprecated after
	  */
	val deprecatedAfterAttName = "deprecatedAfter"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains poke training run id
	  */
	def runIdColumn = table(runIdAttName)
	
	/**
	  * Column that contains poke training poke id
	  */
	def pokeIdColumn = table(pokeIdAttName)
	
	/**
	  * Column that contains poke training level
	  */
	def levelColumn = table(levelAttName)
	
	/**
	  * Column that contains poke training created
	  */
	def createdColumn = table(createdAttName)
	
	/**
	  * Column that contains poke training deprecated after
	  */
	def deprecatedAfterColumn = table(deprecatedAfterAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = PokeTrainingFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: PokeTrainingData) = 
		apply(None, Some(data.runId), Some(data.pokeId), Some(data.level), Some(data.created), 
			data.deprecatedAfter)
	
	override protected def complete(id: Value, data: PokeTrainingData) = PokeTraining(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param created Time when this poke training was added to the database
	  * @return A model containing only the specified created
	  */
	def withCreated(created: Instant) = apply(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when this event was replaced with a more recent recording
	  * @return A model containing only the specified deprecated after
	  */
	def withDeprecatedAfter(deprecatedAfter: Instant) = apply(deprecatedAfter = Some(deprecatedAfter))
	
	/**
	  * @param id A poke training id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param level Reached level
	  * @return A model containing only the specified level
	  */
	def withLevel(level: Int) = apply(level = Some(level))
	
	/**
	  * @param pokeId Id of the trained poke
	  * @return A model containing only the specified poke id
	  */
	def withPokeId(pokeId: Int) = apply(pokeId = Some(pokeId))
	
	/**
	  * @param runId Id of the run on which this event occurred
	  * @return A model containing only the specified run id
	  */
	def withRunId(runId: Int) = apply(runId = Some(runId))
}

/**
  * Used for interacting with PokeTrainings in the database
  * @param id poke training database id
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class PokeTrainingModel(id: Option[Int] = None, runId: Option[Int] = None, pokeId: Option[Int] = None, 
	level: Option[Int] = None, created: Option[Instant] = None, deprecatedAfter: Option[Instant] = None) 
	extends StorableWithFactory[PokeTraining]
{
	// IMPLEMENTED	--------------------
	
	override def factory = PokeTrainingModel.factory
	
	override def valueProperties = {
		import PokeTrainingModel._
		Vector("id" -> id, runIdAttName -> runId, pokeIdAttName -> pokeId, levelAttName -> level, 
			createdAttName -> created, deprecatedAfterAttName -> deprecatedAfter)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param created Time when this poke training was added to the database
	  * @return A new copy of this model with the specified created
	  */
	def withCreated(created: Instant) = copy(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when this event was replaced with a more recent recording
	  * @return A new copy of this model with the specified deprecated after
	  */
	def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
	
	/**
	  * @param level Reached level
	  * @return A new copy of this model with the specified level
	  */
	def withLevel(level: Int) = copy(level = Some(level))
	
	/**
	  * @param pokeId Id of the trained poke
	  * @return A new copy of this model with the specified poke id
	  */
	def withPokeId(pokeId: Int) = copy(pokeId = Some(pokeId))
	
	/**
	  * @param runId Id of the run on which this event occurred
	  * @return A new copy of this model with the specified run id
	  */
	def withRunId(runId: Int) = copy(runId = Some(runId))
}

