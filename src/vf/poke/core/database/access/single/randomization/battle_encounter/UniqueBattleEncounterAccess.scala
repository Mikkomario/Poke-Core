package vf.poke.core.database.access.single.randomization.battle_encounter

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.BattleEncounterFactory
import vf.poke.core.database.model.randomization.BattleEncounterModel
import vf.poke.core.model.stored.randomization.BattleEncounter

object UniqueBattleEncounterAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueBattleEncounterAccess = new _UniqueBattleEncounterAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueBattleEncounterAccess(condition: Condition) extends UniqueBattleEncounterAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct battle encounters.
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniqueBattleEncounterAccess 
	extends SingleRowModelAccess[BattleEncounter] with FilterableView[UniqueBattleEncounterAccess] 
		with DistinctModelAccess[BattleEncounter, Option[BattleEncounter], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * 
		Id of the randomization where this encounter applies. None if no battle encounter (or value) was found.
	  */
	def randomizationId(implicit connection: Connection) = pullColumn(model.randomizationIdColumn).int
	
	/**
	  * Id of the encountered poke. None if no battle encounter (or value) was found.
	  */
	def pokeId(implicit connection: Connection) = pullColumn(model.pokeIdColumn).int
	
	/**
	  * Level at which this poke is encountered. None if no battle encounter (or value) was found.
	  */
	def level(implicit connection: Connection) = pullColumn(model.levelColumn).int
	
	/**
	  * Number of individual encounters represented by this instance. None if no battle encounter (or value)
	  *  was found.
	  */
	def numberOfEncounters(implicit connection: Connection) = pullColumn(model.numberOfEncountersColumn).int
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = BattleEncounterModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = BattleEncounterFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueBattleEncounterAccess = 
		new UniqueBattleEncounterAccess._UniqueBattleEncounterAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the levels of the targeted battle encounters
	  * @param newLevel A new level to assign
	  * @return Whether any battle encounter was affected
	  */
	def level_=(newLevel: Int)(implicit connection: Connection) = putColumn(model.levelColumn, newLevel)
	
	/**
	  * Updates the number of encounterses of the targeted battle encounters
	  * @param newNumberOfEncounters A new number of encounters to assign
	  * @return Whether any battle encounter was affected
	  */
	def numberOfEncounters_=(newNumberOfEncounters: Int)(implicit connection: Connection) = 
		putColumn(model.numberOfEncountersColumn, newNumberOfEncounters)
	
	/**
	  * Updates the poke ids of the targeted battle encounters
	  * @param newPokeId A new poke id to assign
	  * @return Whether any battle encounter was affected
	  */
	def pokeId_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
	
	/**
	  * Updates the randomization ids of the targeted battle encounters
	  * @param newRandomizationId A new randomization id to assign
	  * @return Whether any battle encounter was affected
	  */
	def randomizationId_=(newRandomizationId: Int)(implicit connection: Connection) = 
		putColumn(model.randomizationIdColumn, newRandomizationId)
}

