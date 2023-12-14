package vf.poke.core.database.access.single.randomization.battle_encounter

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.BattleEncounterFactory
import vf.poke.core.database.model.randomization.BattleEncounterModel
import vf.poke.core.model.stored.randomization.BattleEncounter

/**
  * Used for accessing individual battle encounters
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbBattleEncounter extends SingleRowModelAccess[BattleEncounter] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = BattleEncounterModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = BattleEncounterFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted battle encounter
	  * @return An access point to that battle encounter
	  */
	def apply(id: Int) = DbSingleBattleEncounter(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique battle encounters.
	  * @return An access point to the battle encounter that satisfies the specified condition
	  */
	protected
		 def filterDistinct(condition: Condition) = UniqueBattleEncounterAccess(mergeCondition(condition))
}

