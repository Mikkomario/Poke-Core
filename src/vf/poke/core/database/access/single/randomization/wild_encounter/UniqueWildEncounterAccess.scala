package vf.poke.core.database.access.single.randomization.wild_encounter

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.WildEncounterFactory
import vf.poke.core.database.model.randomization.WildEncounterModel
import vf.poke.core.model.stored.randomization.WildEncounter

object UniqueWildEncounterAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueWildEncounterAccess = new _UniqueWildEncounterAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueWildEncounterAccess(condition: Condition) extends UniqueWildEncounterAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct wild encounters.
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniqueWildEncounterAccess 
	extends SingleRowModelAccess[WildEncounter] with FilterableView[UniqueWildEncounterAccess] 
		with DistinctModelAccess[WildEncounter, Option[WildEncounter], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Randomization in which this encounter applies. None if no wild encounter (or value) was found.
	  */
	def randomizationId(implicit connection: Connection) = pullColumn(model.randomizationIdColumn).int
	
	/**
	  * Index of the zone / 
		map in which this encounter applies. None if no wild encounter (or value) was found.
	  */
	def zoneIndex(implicit connection: Connection) = pullColumn(model.zoneIndexColumn).int
	
	/**
	  * Id of the poke that may be encountered. None if no wild encounter (or value) was found.
	  */
	def pokeId(implicit connection: Connection) = pullColumn(model.pokeIdColumn).int
	
	/**
	  * 
		Number of individual encounters represented by this instance. None if no wild encounter (or value) was found.
	  */
	def numberOfEncounters(implicit connection: Connection) = pullColumn(model.numberOfEncountersColumn).int
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = WildEncounterModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = WildEncounterFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueWildEncounterAccess = 
		new UniqueWildEncounterAccess._UniqueWildEncounterAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the number of encounterses of the targeted wild encounters
	  * @param newNumberOfEncounters A new number of encounters to assign
	  * @return Whether any wild encounter was affected
	  */
	def numberOfEncounters_=(newNumberOfEncounters: Int)(implicit connection: Connection) = 
		putColumn(model.numberOfEncountersColumn, newNumberOfEncounters)
	
	/**
	  * Updates the poke ids of the targeted wild encounters
	  * @param newPokeId A new poke id to assign
	  * @return Whether any wild encounter was affected
	  */
	def pokeId_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
	
	/**
	  * Updates the randomization ids of the targeted wild encounters
	  * @param newRandomizationId A new randomization id to assign
	  * @return Whether any wild encounter was affected
	  */
	def randomizationId_=(newRandomizationId: Int)(implicit connection: Connection) = 
		putColumn(model.randomizationIdColumn, newRandomizationId)
	
	/**
	  * Updates the zone indexs of the targeted wild encounters
	  * @param newZoneIndex A new zone index to assign
	  * @return Whether any wild encounter was affected
	  */
	def zoneIndex_=(newZoneIndex: Int)(implicit connection: Connection) = 
		putColumn(model.zoneIndexColumn, newZoneIndex)
}

