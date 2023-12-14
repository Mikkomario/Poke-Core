package vf.poke.core.database.model.randomization

import utopia.flow.collection.immutable.range.NumericSpan
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.core.database.factory.randomization.WildEncounterFactory
import vf.poke.core.model.partial.randomization.WildEncounterData
import vf.poke.core.model.stored.randomization.WildEncounter

/**
  * Used for constructing WildEncounterModel instances and for inserting wild encounters to the database
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object WildEncounterModel extends DataInserter[WildEncounterModel, WildEncounter, WildEncounterData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains wild encounter randomization id
	  */
	val randomizationIdAttName = "randomizationId"
	
	/**
	  * Name of the property that contains wild encounter zone index
	  */
	val zoneIndexAttName = "zoneIndex"
	
	/**
	  * Name of the property that contains wild encounter poke id
	  */
	val pokeIdAttName = "pokeId"
	
	/**
	  * Name of the property that contains wild encounter min level
	  */
	val minLevelAttName = "minLevel"
	
	/**
	  * Name of the property that contains wild encounter max level
	  */
	val maxLevelAttName = "maxLevel"
	
	/**
	  * Name of the property that contains wild encounter number of encounters
	  */
	val numberOfEncountersAttName = "numberOfEncounters"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains wild encounter randomization id
	  */
	def randomizationIdColumn = table(randomizationIdAttName)
	
	/**
	  * Column that contains wild encounter zone index
	  */
	def zoneIndexColumn = table(zoneIndexAttName)
	
	/**
	  * Column that contains wild encounter poke id
	  */
	def pokeIdColumn = table(pokeIdAttName)
	
	/**
	  * Column that contains wild encounter min level
	  */
	def minLevelColumn = table(minLevelAttName)
	
	/**
	  * Column that contains wild encounter max level
	  */
	def maxLevelColumn = table(maxLevelAttName)
	
	/**
	  * Column that contains wild encounter number of encounters
	  */
	def numberOfEncountersColumn = table(numberOfEncountersAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = WildEncounterFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: WildEncounterData) = 
		apply(None, Some(data.randomizationId), Some(data.zoneIndex), Some(data.pokeId), 
			Some(data.levelRange.start), Some(data.levelRange.end), Some(data.numberOfEncounters))
	
	override protected def complete(id: Value, data: WildEncounterData) = WildEncounter(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param id A wild encounter id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param levelRange Range of levels that are possible for this encounter
	  * @return A model containing only the specified level range (sets all 2 values)
	  */
	def withLevelRange(levelRange: NumericSpan[Int]) = 
		apply(minLevel = Some(levelRange.start), maxLevel = Some(levelRange.end))
	
	/**
	  * @param numberOfEncounters Number of individual encounters represented by this instance
	  * @return A model containing only the specified number of encounters
	  */
	def withNumberOfEncounters(numberOfEncounters: Int) = apply(numberOfEncounters = Some(numberOfEncounters))
	
	/**
	  * @param pokeId Id of the poke that may be encountered
	  * @return A model containing only the specified poke id
	  */
	def withPokeId(pokeId: Int) = apply(pokeId = Some(pokeId))
	
	/**
	  * @param randomizationId Randomization in which this encounter applies
	  * @return A model containing only the specified randomization id
	  */
	def withRandomizationId(randomizationId: Int) = apply(randomizationId = Some(randomizationId))
	
	/**
	  * @param zoneIndex Index of the zone / map in which this encounter applies
	  * @return A model containing only the specified zone index
	  */
	def withZoneIndex(zoneIndex: Int) = apply(zoneIndex = Some(zoneIndex))
}

/**
  * Used for interacting with WildEncounters in the database
  * @param id wild encounter database id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class WildEncounterModel(id: Option[Int] = None, randomizationId: Option[Int] = None, 
	zoneIndex: Option[Int] = None, pokeId: Option[Int] = None, minLevel: Option[Int] = None, 
	maxLevel: Option[Int] = None, numberOfEncounters: Option[Int] = None) 
	extends StorableWithFactory[WildEncounter]
{
	// IMPLEMENTED	--------------------
	
	override def factory = WildEncounterModel.factory
	
	override def valueProperties = {
		import WildEncounterModel._
		Vector("id" -> id, randomizationIdAttName -> randomizationId, zoneIndexAttName -> zoneIndex, 
			pokeIdAttName -> pokeId, minLevelAttName -> minLevel, maxLevelAttName -> maxLevel, 
			numberOfEncountersAttName -> numberOfEncounters)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param levelRange Range of levels that are possible for this encounter
	  * @return A new copy of this model with the specified level range (sets all 2 values)
	  */
	def withLevelRange(levelRange: NumericSpan[Int]) = 
		copy(minLevel = Some(levelRange.start), maxLevel = Some(levelRange.end))
	
	/**
	  * @param numberOfEncounters Number of individual encounters represented by this instance
	  * @return A new copy of this model with the specified number of encounters
	  */
	def withNumberOfEncounters(numberOfEncounters: Int) = copy(numberOfEncounters = Some(numberOfEncounters))
	
	/**
	  * @param pokeId Id of the poke that may be encountered
	  * @return A new copy of this model with the specified poke id
	  */
	def withPokeId(pokeId: Int) = copy(pokeId = Some(pokeId))
	
	/**
	  * @param randomizationId Randomization in which this encounter applies
	  * @return A new copy of this model with the specified randomization id
	  */
	def withRandomizationId(randomizationId: Int) = copy(randomizationId = Some(randomizationId))
	
	/**
	  * @param zoneIndex Index of the zone / map in which this encounter applies
	  * @return A new copy of this model with the specified zone index
	  */
	def withZoneIndex(zoneIndex: Int) = copy(zoneIndex = Some(zoneIndex))
}

