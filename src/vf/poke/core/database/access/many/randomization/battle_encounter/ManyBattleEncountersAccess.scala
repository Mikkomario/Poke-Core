package vf.poke.core.database.access.many.randomization.battle_encounter

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.BattleEncounterFactory
import vf.poke.core.database.model.randomization.BattleEncounterModel
import vf.poke.core.model.stored.randomization.BattleEncounter

object ManyBattleEncountersAccess
{
	// NESTED	--------------------
	
	private class ManyBattleEncountersSubView(condition: Condition) extends ManyBattleEncountersAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple battle encounters at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait ManyBattleEncountersAccess 
	extends ManyRowModelAccess[BattleEncounter] with FilterableView[ManyBattleEncountersAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * randomization ids of the accessible battle encounters
	  */
	def randomizationIds(implicit connection: Connection) = 
		pullColumn(model.randomizationIdColumn).map { v => v.getInt }
	
	/**
	  * poke ids of the accessible battle encounters
	  */
	def pokeIds(implicit connection: Connection) = pullColumn(model.pokeIdColumn).map { v => v.getInt }
	
	/**
	  * levels of the accessible battle encounters
	  */
	def levels(implicit connection: Connection) = pullColumn(model.levelColumn).map { v => v.getInt }
	
	/**
	  * number of encounterses of the accessible battle encounters
	  */
	def numberOfEncounterses(implicit connection: Connection) = 
		pullColumn(model.numberOfEncountersColumn).map { v => v.getInt }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = BattleEncounterModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = BattleEncounterFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyBattleEncountersAccess = 
		new ManyBattleEncountersAccess.ManyBattleEncountersSubView(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the levels of the targeted battle encounters
	  * @param newLevel A new level to assign
	  * @return Whether any battle encounter was affected
	  */
	def levels_=(newLevel: Int)(implicit connection: Connection) = putColumn(model.levelColumn, newLevel)
	
	/**
	  * Updates the number of encounterses of the targeted battle encounters
	  * @param newNumberOfEncounters A new number of encounters to assign
	  * @return Whether any battle encounter was affected
	  */
	def numberOfEncounterses_=(newNumberOfEncounters: Int)(implicit connection: Connection) = 
		putColumn(model.numberOfEncountersColumn, newNumberOfEncounters)
	
	/**
	  * Updates the poke ids of the targeted battle encounters
	  * @param newPokeId A new poke id to assign
	  * @return Whether any battle encounter was affected
	  */
	def pokeIds_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
	
	/**
	  * Updates the randomization ids of the targeted battle encounters
	  * @param newRandomizationId A new randomization id to assign
	  * @return Whether any battle encounter was affected
	  */
	def randomizationIds_=(newRandomizationId: Int)(implicit connection: Connection) = 
		putColumn(model.randomizationIdColumn, newRandomizationId)
}

