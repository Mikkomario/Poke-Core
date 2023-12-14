package vf.poke.core.database.access.many.poke.ability

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.PokeAbilityFactory
import vf.poke.core.database.model.poke.PokeAbilityModel
import vf.poke.core.model.stored.poke.PokeAbility

object ManyPokeAbilitiesAccess
{
	// NESTED	--------------------
	
	private class ManyPokeAbilitiesSubView(condition: Condition) extends ManyPokeAbilitiesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple poke abilities at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait ManyPokeAbilitiesAccess 
	extends ManyPokeAbilitiesAccessLike[PokeAbility, ManyPokeAbilitiesAccess] with ManyRowModelAccess[PokeAbility]
{
	// IMPLEMENTED	--------------------
	
	override def factory = PokeAbilityFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyPokeAbilitiesAccess = 
		new ManyPokeAbilitiesAccess.ManyPokeAbilitiesSubView(mergeCondition(filterCondition))
}

