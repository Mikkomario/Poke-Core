package vf.poke.core.database.access.many.poke

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.PokeFactory
import vf.poke.core.model.stored.poke.Poke

object ManyPokesAccess
{
	// NESTED	--------------------
	
	private class ManyPokesSubView(condition: Condition) extends ManyPokesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple pokes at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait ManyPokesAccess extends ManyPokesAccessLike[Poke, ManyPokesAccess] with ManyRowModelAccess[Poke]
{
	// IMPLEMENTED	--------------------
	
	override def factory = PokeFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyPokesAccess = 
		new ManyPokesAccess.ManyPokesSubView(mergeCondition(filterCondition))
}

