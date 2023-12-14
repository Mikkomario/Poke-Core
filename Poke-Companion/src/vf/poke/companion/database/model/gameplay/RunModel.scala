package vf.poke.companion.database.model.gameplay

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.companion.database.factory.gameplay.RunFactory
import vf.poke.companion.model.partial.gameplay.RunData
import vf.poke.companion.model.stored.gameplay.Run

import java.time.Instant

/**
  * Used for constructing RunModel instances and for inserting runs to the database
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object RunModel extends DataInserter[RunModel, Run, RunData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains run name
	  */
	val nameAttName = "name"
	
	/**
	  * Name of the property that contains run randomization id
	  */
	val randomizationIdAttName = "randomizationId"
	
	/**
	  * Name of the property that contains run created
	  */
	val createdAttName = "created"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains run name
	  */
	def nameColumn = table(nameAttName)
	
	/**
	  * Column that contains run randomization id
	  */
	def randomizationIdColumn = table(randomizationIdAttName)
	
	/**
	  * Column that contains run created
	  */
	def createdColumn = table(createdAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = RunFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: RunData) = apply(None, data.name, Some(data.randomizationId), Some(data.created))
	
	override protected def complete(id: Value, data: RunData) = Run(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param created Time when this run was added to the database
	  * @return A model containing only the specified created
	  */
	def withCreated(created: Instant) = apply(created = Some(created))
	
	/**
	  * @param id A run id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param name Name of this run, for example consisting of the player name
	  * @return A model containing only the specified name
	  */
	def withName(name: String) = apply(name = name)
	
	/**
	  * @param randomizationId Id of the randomization that's applied to this game run
	  * @return A model containing only the specified randomization id
	  */
	def withRandomizationId(randomizationId: Int) = apply(randomizationId = Some(randomizationId))
}

/**
  * Used for interacting with Runs in the database
  * @param id run database id
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class RunModel(id: Option[Int] = None, name: String = "", randomizationId: Option[Int] = None, 
	created: Option[Instant] = None) 
	extends StorableWithFactory[Run]
{
	// IMPLEMENTED	--------------------
	
	override def factory = RunModel.factory
	
	override def valueProperties = {
		import RunModel._
		Vector("id" -> id, nameAttName -> name, randomizationIdAttName -> randomizationId, 
			createdAttName -> created)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param created Time when this run was added to the database
	  * @return A new copy of this model with the specified created
	  */
	def withCreated(created: Instant) = copy(created = Some(created))
	
	/**
	  * @param name Name of this run, for example consisting of the player name
	  * @return A new copy of this model with the specified name
	  */
	def withName(name: String) = copy(name = name)
	
	/**
	  * @param randomizationId Id of the randomization that's applied to this game run
	  * @return A new copy of this model with the specified randomization id
	  */
	def withRandomizationId(randomizationId: Int) = copy(randomizationId = Some(randomizationId))
}

