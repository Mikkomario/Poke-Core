package vf.poke.core.database.access.single.randomization.wild_encounter

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.WildEncounterFactory
import vf.poke.core.database.model.randomization.WildEncounterModel
import vf.poke.core.model.stored.randomization.WildEncounter

/**
  * Used for accessing individual wild encounters
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbWildEncounter extends SingleRowModelAccess[WildEncounter] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = WildEncounterModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = WildEncounterFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted wild encounter
	  * @return An access point to that wild encounter
	  */
	def apply(id: Int) = DbSingleWildEncounter(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique wild encounters.
	  * @return An access point to the wild encounter that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniqueWildEncounterAccess(mergeCondition(condition))
}

