package vf.poke.core.database.model.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.core.database.factory.poke.EvoFactory
import vf.poke.core.model.partial.poke.EvoData
import vf.poke.core.model.stored.poke.Evo

/**
  * Used for constructing EvoModel instances and for inserting evos to the database
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object EvoModel extends DataInserter[EvoModel, Evo, EvoData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains evo from id
	  */
	val fromIdAttName = "fromId"
	
	/**
	  * Name of the property that contains evo to id
	  */
	val toIdAttName = "toId"
	
	/**
	  * Name of the property that contains evo level threshold
	  */
	val levelThresholdAttName = "levelThreshold"
	
	/**
	  * Name of the property that contains evo item id
	  */
	val itemIdAttName = "itemId"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains evo from id
	  */
	def fromIdColumn = table(fromIdAttName)
	
	/**
	  * Column that contains evo to id
	  */
	def toIdColumn = table(toIdAttName)
	
	/**
	  * Column that contains evo level threshold
	  */
	def levelThresholdColumn = table(levelThresholdAttName)
	
	/**
	  * Column that contains evo item id
	  */
	def itemIdColumn = table(itemIdAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = EvoFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: EvoData) = 
		apply(None, Some(data.fromId), Some(data.toId), data.levelThreshold, data.itemId)
	
	override protected def complete(id: Value, data: EvoData) = Evo(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param fromId Id of the poke from which this evo originates
	  * @return A model containing only the specified from id
	  */
	def withFromId(fromId: Int) = apply(fromId = Some(fromId))
	
	/**
	  * @param id A evo id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param itemId Id of the item associated with this evo
	  * @return A model containing only the specified item id
	  */
	def withItemId(itemId: Int) = apply(itemId = Some(itemId))
	
	/**
	  * @param levelThreshold The level at which this evo is enabled. None if not level-based
	  * @return A model containing only the specified level threshold
	  */
	def withLevelThreshold(levelThreshold: Int) = apply(levelThreshold = Some(levelThreshold))
	
	/**
	  * @param toId Id of the poke to which this evo leads
	  * @return A model containing only the specified to id
	  */
	def withToId(toId: Int) = apply(toId = Some(toId))
}

/**
  * Used for interacting with Evos in the database
  * @param id evo database id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class EvoModel(id: Option[Int] = None, fromId: Option[Int] = None, toId: Option[Int] = None, 
	levelThreshold: Option[Int] = None, itemId: Option[Int] = None) 
	extends StorableWithFactory[Evo]
{
	// IMPLEMENTED	--------------------
	
	override def factory = EvoModel.factory
	
	override def valueProperties = {
		import EvoModel._
		Vector("id" -> id, fromIdAttName -> fromId, toIdAttName -> toId, 
			levelThresholdAttName -> levelThreshold, itemIdAttName -> itemId)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param fromId Id of the poke from which this evo originates
	  * @return A new copy of this model with the specified from id
	  */
	def withFromId(fromId: Int) = copy(fromId = Some(fromId))
	
	/**
	  * @param itemId Id of the item associated with this evo
	  * @return A new copy of this model with the specified item id
	  */
	def withItemId(itemId: Int) = copy(itemId = Some(itemId))
	
	/**
	  * @param levelThreshold The level at which this evo is enabled. None if not level-based
	  * @return A new copy of this model with the specified level threshold
	  */
	def withLevelThreshold(levelThreshold: Int) = copy(levelThreshold = Some(levelThreshold))
	
	/**
	  * @param toId Id of the poke to which this evo leads
	  * @return A new copy of this model with the specified to id
	  */
	def withToId(toId: Int) = copy(toId = Some(toId))
}

