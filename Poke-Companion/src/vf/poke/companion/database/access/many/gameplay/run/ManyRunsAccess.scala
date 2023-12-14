package vf.poke.companion.database.access.many.gameplay.run

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.ChronoRowFactoryView
import utopia.vault.sql.Condition
import vf.poke.companion.database.factory.gameplay.RunFactory
import vf.poke.companion.model.stored.gameplay.Run

object ManyRunsAccess
{
	// NESTED	--------------------
	
	private class ManyRunsSubView(condition: Condition) extends ManyRunsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple runs at a time
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
trait ManyRunsAccess 
	extends ManyRunsAccessLike[Run, ManyRunsAccess] with ManyRowModelAccess[Run] 
		with ChronoRowFactoryView[Run, ManyRunsAccess]
{
	// COMPUTED ------------------------
	
	def detailed = DbDetailedRuns.filter(accessCondition)
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = RunFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyRunsAccess = 
		new ManyRunsAccess.ManyRunsSubView(mergeCondition(filterCondition))
}

