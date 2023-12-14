package vf.poke.companion.database.model.gameplay

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.companion.database.factory.gameplay.PokeCaptureFactory
import vf.poke.companion.model.partial.gameplay.PokeCaptureData
import vf.poke.companion.model.stored.gameplay.PokeCapture

import java.time.Instant

/**
  * Used for constructing PokeCaptureModel instances and for inserting poke captures to the database
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object PokeCaptureModel extends DataInserter[PokeCaptureModel, PokeCapture, PokeCaptureData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains poke capture run id
	  */
	val runIdAttName = "runId"
	
	/**
	  * Name of the property that contains poke capture poke id
	  */
	val pokeIdAttName = "pokeId"
	
	/**
	  * Name of the property that contains poke capture level
	  */
	val levelAttName = "level"
	
	/**
	  * Name of the property that contains poke capture created
	  */
	val createdAttName = "created"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains poke capture run id
	  */
	def runIdColumn = table(runIdAttName)
	
	/**
	  * Column that contains poke capture poke id
	  */
	def pokeIdColumn = table(pokeIdAttName)
	
	/**
	  * Column that contains poke capture level
	  */
	def levelColumn = table(levelAttName)
	
	/**
	  * Column that contains poke capture created
	  */
	def createdColumn = table(createdAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = PokeCaptureFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: PokeCaptureData) = 
		apply(None, Some(data.runId), Some(data.pokeId), Some(data.level), Some(data.created))
	
	override protected def complete(id: Value, data: PokeCaptureData) = PokeCapture(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param created Time when this poke capture was added to the database
	  * @return A model containing only the specified created
	  */
	def withCreated(created: Instant) = apply(created = Some(created))
	
	/**
	  * @param id A poke capture id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param level Level at which this capture was made
	  * @return A model containing only the specified level
	  */
	def withLevel(level: Int) = apply(level = Some(level))
	
	/**
	  * @param pokeId Id of the captured poke
	  * @return A model containing only the specified poke id
	  */
	def withPokeId(pokeId: Int) = apply(pokeId = Some(pokeId))
	
	/**
	  * @param runId Id of the game run on which this capture was made
	  * @return A model containing only the specified run id
	  */
	def withRunId(runId: Int) = apply(runId = Some(runId))
}

/**
  * Used for interacting with PokeCaptures in the database
  * @param id poke capture database id
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class PokeCaptureModel(id: Option[Int] = None, runId: Option[Int] = None, pokeId: Option[Int] = None, 
	level: Option[Int] = None, created: Option[Instant] = None) 
	extends StorableWithFactory[PokeCapture]
{
	// IMPLEMENTED	--------------------
	
	override def factory = PokeCaptureModel.factory
	
	override def valueProperties = {
		import PokeCaptureModel._
		Vector("id" -> id, runIdAttName -> runId, pokeIdAttName -> pokeId, levelAttName -> level, 
			createdAttName -> created)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param created Time when this poke capture was added to the database
	  * @return A new copy of this model with the specified created
	  */
	def withCreated(created: Instant) = copy(created = Some(created))
	
	/**
	  * @param level Level at which this capture was made
	  * @return A new copy of this model with the specified level
	  */
	def withLevel(level: Int) = copy(level = Some(level))
	
	/**
	  * @param pokeId Id of the captured poke
	  * @return A new copy of this model with the specified poke id
	  */
	def withPokeId(pokeId: Int) = copy(pokeId = Some(pokeId))
	
	/**
	  * @param runId Id of the game run on which this capture was made
	  * @return A new copy of this model with the specified run id
	  */
	def withRunId(runId: Int) = copy(runId = Some(runId))
}

