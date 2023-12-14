package vf.poke.core.database.access.many.randomization.starter_set

import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.StarterSetFactory
import vf.poke.core.database.model.randomization.StarterAssignmentModel
import vf.poke.core.model.stored.randomization.StarterSet

object ManyStarterSetsAccess
{
	// NESTED	--------------------
	
	private class ManyStarterSetsSubView(condition: Condition) extends ManyStarterSetsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple starter sets at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait ManyStarterSetsAccess 
	extends ManyStarterSetsAccessLike[StarterSet, ManyStarterSetsAccess] with ManyRowModelAccess[StarterSet]
{
	// COMPUTED ------------------------
	
	protected def assignmentModel = StarterAssignmentModel
	
	/**
	 * @param connection Implicit DB connection
	 * @return Ids of the accessible starter pokes
	 */
	def pokeIds(implicit connection: Connection) =
		pullColumn(assignmentModel.pokeIdColumn, assignmentModel.table).map { _.getInt }.toSet
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = StarterSetFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyStarterSetsAccess = 
		new ManyStarterSetsAccess.ManyStarterSetsSubView(mergeCondition(filterCondition))
}

