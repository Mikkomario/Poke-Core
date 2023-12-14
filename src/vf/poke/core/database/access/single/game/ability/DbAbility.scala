package vf.poke.core.database.access.single.game.ability

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.game.AbilityFactory
import vf.poke.core.database.model.game.AbilityModel
import vf.poke.core.model.stored.game.Ability

/**
  * Used for accessing individual abilities
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbAbility extends SingleRowModelAccess[Ability] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = AbilityModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = AbilityFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted ability
	  * @return An access point to that ability
	  */
	def apply(id: Int) = DbSingleAbility(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique abilities.
	  * @return An access point to the ability that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniqueAbilityAccess(mergeCondition(condition))
}

