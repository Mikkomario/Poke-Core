package vf.poke.companion.database.access.many.gameplay.run

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.factory.row.FromRowFactoryWithTimestamps
import utopia.vault.nosql.view.ChronoRowFactoryView
import utopia.vault.sql.Condition
import vf.poke.companion.database.factory.gameplay.DetailedRunFactory
import vf.poke.companion.model.combined.gameplay.DetailedRun
import vf.poke.core.database.model.game.GameModel
import vf.poke.core.database.model.randomization.RandomizationModel

object ManyDetailedRunsAccess
{
	private class DetailedRunSubView(condition: Condition) extends ManyDetailedRunsAccess
	{
		override def accessCondition: Option[Condition] = Some(condition)
	}
}

/**
 * Used for accessing multiple detailed run instances at a time
 * @author Mikko Hilpinen
 * @since 2.12.2023, v1.0
 */
trait ManyDetailedRunsAccess
	extends ManyRunsAccessLike[DetailedRun, ManyDetailedRunsAccess] with ManyRowModelAccess[DetailedRun]
		with ChronoRowFactoryView[DetailedRun, ManyDetailedRunsAccess]
{
	// PROTECTED    ------------------------
	
	protected def gameModel = GameModel
	protected def randomizationModel = RandomizationModel
	
	
	// IMPLEMENTED  ------------------------
	
	override protected def self: ManyDetailedRunsAccess = this
	override def factory: FromRowFactoryWithTimestamps[DetailedRun] = DetailedRunFactory
	
	override def filter(additionalCondition: Condition): ManyDetailedRunsAccess =
		new ManyDetailedRunsAccess.DetailedRunSubView(mergeCondition(additionalCondition))
		
	
	// OTHER    ----------------------------
	
	def withGameNameContaining(namePart: String) =
		filter(gameModel.nameColumn.contains(namePart))
}
