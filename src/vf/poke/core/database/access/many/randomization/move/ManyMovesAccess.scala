package vf.poke.core.database.access.many.randomization.move

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.MoveFactory
import vf.poke.core.database.model.randomization.MoveModel
import vf.poke.core.model.enumeration.{CriticalRate, MoveCategory, PokeType}
import vf.poke.core.model.stored.randomization.Move

object ManyMovesAccess
{
	// NESTED	--------------------
	
	private class ManyMovesSubView(condition: Condition) extends ManyMovesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple moves at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait ManyMovesAccess 
	extends ManyMovesAccessLike[Move, ManyMovesAccess] with ManyRowModelAccess[Move]
{
	// IMPLEMENTED	--------------------
	
	override def factory = MoveFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyMovesAccess = 
		new ManyMovesAccess.ManyMovesSubView(mergeCondition(filterCondition))
}

