package vf.poke.companion.database.access.single.gameplay.run

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.companion.database.factory.gameplay.RunFactory
import vf.poke.companion.database.model.gameplay.RunModel
import vf.poke.companion.model.stored.gameplay.Run

/**
  * Used for accessing individual runs
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object DbRun extends SingleRowModelAccess[Run] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = RunModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = RunFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted run
	  * @return An access point to that run
	  */
	def apply(id: Int) = DbSingleRun(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique runs.
	  * @return An access point to the run that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniqueRunAccess(mergeCondition(condition))
}

