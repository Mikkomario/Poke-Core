package vf.poke.core.database.model.randomization

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.core.database.factory.randomization.BattleEncounterFactory
import vf.poke.core.model.partial.randomization.BattleEncounterData
import vf.poke.core.model.stored.randomization.BattleEncounter

/**
  * Used for constructing BattleEncounterModel instances and for inserting battle encounters to the database
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object BattleEncounterModel extends DataInserter[BattleEncounterModel, BattleEncounter, BattleEncounterData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains battle encounter randomization id
	  */
	val randomizationIdAttName = "randomizationId"
	
	/**
	  * Name of the property that contains battle encounter poke id
	  */
	val pokeIdAttName = "pokeId"
	
	/**
	  * Name of the property that contains battle encounter level
	  */
	val levelAttName = "level"
	
	/**
	  * Name of the property that contains battle encounter number of encounters
	  */
	val numberOfEncountersAttName = "numberOfEncounters"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains battle encounter randomization id
	  */
	def randomizationIdColumn = table(randomizationIdAttName)
	
	/**
	  * Column that contains battle encounter poke id
	  */
	def pokeIdColumn = table(pokeIdAttName)
	
	/**
	  * Column that contains battle encounter level
	  */
	def levelColumn = table(levelAttName)
	
	/**
	  * Column that contains battle encounter number of encounters
	  */
	def numberOfEncountersColumn = table(numberOfEncountersAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = BattleEncounterFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: BattleEncounterData) = 
		apply(None, Some(data.randomizationId), Some(data.pokeId), Some(data.level), 
			Some(data.numberOfEncounters))
	
	override protected def complete(id: Value, data: BattleEncounterData) = BattleEncounter(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param id A battle encounter id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param level Level at which this poke is encountered
	  * @return A model containing only the specified level
	  */
	def withLevel(level: Int) = apply(level = Some(level))
	
	/**
	  * @param numberOfEncounters Number of individual encounters represented by this instance
	  * @return A model containing only the specified number of encounters
	  */
	def withNumberOfEncounters(numberOfEncounters: Int) = apply(numberOfEncounters = Some(numberOfEncounters))
	
	/**
	  * @param pokeId Id of the encountered poke
	  * @return A model containing only the specified poke id
	  */
	def withPokeId(pokeId: Int) = apply(pokeId = Some(pokeId))
	
	/**
	  * @param randomizationId Id of the randomization where this encounter applies
	  * @return A model containing only the specified randomization id
	  */
	def withRandomizationId(randomizationId: Int) = apply(randomizationId = Some(randomizationId))
}

/**
  * Used for interacting with BattleEncounters in the database
  * @param id battle encounter database id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class BattleEncounterModel(id: Option[Int] = None, randomizationId: Option[Int] = None, 
	pokeId: Option[Int] = None, level: Option[Int] = None, numberOfEncounters: Option[Int] = None) 
	extends StorableWithFactory[BattleEncounter]
{
	// IMPLEMENTED	--------------------
	
	override def factory = BattleEncounterModel.factory
	
	override def valueProperties = {
		import BattleEncounterModel._
		Vector("id" -> id, randomizationIdAttName -> randomizationId, pokeIdAttName -> pokeId, 
			levelAttName -> level, numberOfEncountersAttName -> numberOfEncounters)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param level Level at which this poke is encountered
	  * @return A new copy of this model with the specified level
	  */
	def withLevel(level: Int) = copy(level = Some(level))
	
	/**
	  * @param numberOfEncounters Number of individual encounters represented by this instance
	  * @return A new copy of this model with the specified number of encounters
	  */
	def withNumberOfEncounters(numberOfEncounters: Int) = copy(numberOfEncounters = Some(numberOfEncounters))
	
	/**
	  * @param pokeId Id of the encountered poke
	  * @return A new copy of this model with the specified poke id
	  */
	def withPokeId(pokeId: Int) = copy(pokeId = Some(pokeId))
	
	/**
	  * @param randomizationId Id of the randomization where this encounter applies
	  * @return A new copy of this model with the specified randomization id
	  */
	def withRandomizationId(randomizationId: Int) = copy(randomizationId = Some(randomizationId))
}

