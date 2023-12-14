package vf.poke.core.database.access.many.randomization.wild_encounter

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.WildEncounterFactory
import vf.poke.core.database.model.randomization.WildEncounterModel
import vf.poke.core.model.stored.randomization.WildEncounter

object ManyWildEncountersAccess
{
	// NESTED	--------------------
	
	private class ManyWildEncountersSubView(condition: Condition) extends ManyWildEncountersAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple wild encounters at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait ManyWildEncountersAccess 
	extends ManyRowModelAccess[WildEncounter] with FilterableView[ManyWildEncountersAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * randomization ids of the accessible wild encounters
	  */
	def randomizationIds(implicit connection: Connection) = 
		pullColumn(model.randomizationIdColumn).map { v => v.getInt }
	
	/**
	  * zone indexs of the accessible wild encounters
	  */
	def zoneIndexs(implicit connection: Connection) = pullColumn(model.zoneIndexColumn).map { v => v.getInt }
	
	/**
	  * poke ids of the accessible wild encounters
	  */
	def pokeIds(implicit connection: Connection) = pullColumn(model.pokeIdColumn).map { v => v.getInt }
	
	/**
	  * number of encounterses of the accessible wild encounters
	  */
	def encounterCounts(implicit connection: Connection) =
		pullColumn(model.numberOfEncountersColumn).map { v => v.getInt }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = WildEncounterModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = WildEncounterFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyWildEncountersAccess = 
		new ManyWildEncountersAccess.ManyWildEncountersSubView(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	 * @param randomizationId Id of the targeted randomization
	 * @return Access to wild encounters in that randomization
	 */
	def inRandomization(randomizationId: Int) =
		filter(model.withRandomizationId(randomizationId).toCondition)
	
	/**
	  * Updates the number of encounterses of the targeted wild encounters
	  * @param newNumberOfEncounters A new number of encounters to assign
	  * @return Whether any wild encounter was affected
	  */
	def encounterCounts_=(newNumberOfEncounters: Int)(implicit connection: Connection) =
		putColumn(model.numberOfEncountersColumn, newNumberOfEncounters)
	
	/**
	  * Updates the poke ids of the targeted wild encounters
	  * @param newPokeId A new poke id to assign
	  * @return Whether any wild encounter was affected
	  */
	def pokeIds_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
	
	/**
	  * Updates the randomization ids of the targeted wild encounters
	  * @param newRandomizationId A new randomization id to assign
	  * @return Whether any wild encounter was affected
	  */
	def randomizationIds_=(newRandomizationId: Int)(implicit connection: Connection) = 
		putColumn(model.randomizationIdColumn, newRandomizationId)
	
	/**
	  * Updates the zone indexs of the targeted wild encounters
	  * @param newZoneIndex A new zone index to assign
	  * @return Whether any wild encounter was affected
	  */
	def zoneIndexs_=(newZoneIndex: Int)(implicit connection: Connection) = 
		putColumn(model.zoneIndexColumn, newZoneIndex)
}

