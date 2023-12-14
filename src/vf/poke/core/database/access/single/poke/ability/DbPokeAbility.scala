package vf.poke.core.database.access.single.poke.ability

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.PokeAbilityFactory
import vf.poke.core.database.model.poke.PokeAbilityModel
import vf.poke.core.model.stored.poke.PokeAbility

/**
  * Used for accessing individual poke abilities
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbPokeAbility extends SingleRowModelAccess[PokeAbility] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = PokeAbilityModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PokeAbilityFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted poke ability
	  * @return An access point to that poke ability
	  */
	def apply(id: Int) = DbSinglePokeAbility(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique poke abilities.
	  * @return An access point to the poke ability that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniquePokeAbilityAccess(mergeCondition(condition))
}

