package vf.poke.core.database.model.randomization

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.core.database.factory.randomization.RandomizationFactory
import vf.poke.core.model.partial.randomization.RandomizationData
import vf.poke.core.model.stored.randomization.Randomization

import java.time.Instant

/**
  * Used for constructing RandomizationModel instances and for inserting randomizations to the database
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object RandomizationModel extends DataInserter[RandomizationModel, Randomization, RandomizationData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains randomization game id
	  */
	val gameIdAttName = "gameId"
	
	/**
	  * Name of the property that contains randomization created
	  */
	val createdAttName = "created"
	
	/**
	  * Name of the property that contains randomization is original
	  */
	val isOriginalAttName = "isOriginal"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains randomization game id
	  */
	def gameIdColumn = table(gameIdAttName)
	
	/**
	  * Column that contains randomization created
	  */
	def createdColumn = table(createdAttName)
	
	/**
	  * Column that contains randomization is original
	  */
	def isOriginalColumn = table(isOriginalAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = RandomizationFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: RandomizationData) = 
		apply(None, Some(data.gameId), Some(data.created), Some(data.isOriginal))
	
	override protected def complete(id: Value, data: RandomizationData) = Randomization(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param created Time when this randomization was added to the database
	  * @return A model containing only the specified created
	  */
	def withCreated(created: Instant) = apply(created = Some(created))
	
	/**
	  * @param gameId Id of the targeted game / ROM
	  * @return A model containing only the specified game id
	  */
	def withGameId(gameId: Int) = apply(gameId = Some(gameId))
	
	/**
	  * @param id A randomization id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param isOriginal True if this represents an unmodified copy of the game
	  * @return A model containing only the specified is original
	  */
	def withIsOriginal(isOriginal: Boolean) = apply(isOriginal = Some(isOriginal))
}

/**
  * Used for interacting with Randomizations in the database
  * @param id randomization database id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class RandomizationModel(id: Option[Int] = None, gameId: Option[Int] = None, 
	created: Option[Instant] = None, isOriginal: Option[Boolean] = None) 
	extends StorableWithFactory[Randomization]
{
	// IMPLEMENTED	--------------------
	
	override def factory = RandomizationModel.factory
	
	override def valueProperties = {
		import RandomizationModel._
		Vector("id" -> id, gameIdAttName -> gameId, createdAttName -> created, 
			isOriginalAttName -> isOriginal)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param created Time when this randomization was added to the database
	  * @return A new copy of this model with the specified created
	  */
	def withCreated(created: Instant) = copy(created = Some(created))
	
	/**
	  * @param gameId Id of the targeted game / ROM
	  * @return A new copy of this model with the specified game id
	  */
	def withGameId(gameId: Int) = copy(gameId = Some(gameId))
	
	/**
	  * @param isOriginal True if this represents an unmodified copy of the game
	  * @return A new copy of this model with the specified is original
	  */
	def withIsOriginal(isOriginal: Boolean) = copy(isOriginal = Some(isOriginal))
}

