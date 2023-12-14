package vf.poke.core.database.access.single.poke.ability

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.DetailedPokeAbilityFactory
import vf.poke.core.database.model.game.AbilityModel
import vf.poke.core.database.model.poke.PokeAbilityModel
import vf.poke.core.model.combined.poke.DetailedPokeAbility

/**
  * Used for accessing individual detailed poke abilities
  * @author Mikko Hilpinen
  * @since 13.12.2023, v1.0
  */
object DbDetailedPokeAbility 
	extends SingleRowModelAccess[DetailedPokeAbility] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with linked poke abilities
	  */
	protected def model = PokeAbilityModel
	
	/**
	  * A database model (factory) used for interacting with the linked ability
	  */
	protected def abilityModel = AbilityModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = DetailedPokeAbilityFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted detailed poke ability
	  * @return An access point to that detailed poke ability
	  */
	def apply(id: Int) = DbSingleDetailedPokeAbility(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique detailed poke abilities.
	  * @return An access point to the detailed poke ability that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = 
		UniqueDetailedPokeAbilityAccess(mergeCondition(condition))
}

