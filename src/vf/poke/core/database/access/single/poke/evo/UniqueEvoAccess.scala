package vf.poke.core.database.access.single.poke.evo

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.EvoFactory
import vf.poke.core.database.model.poke.EvoModel
import vf.poke.core.model.stored.poke.Evo

object UniqueEvoAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueEvoAccess = new _UniqueEvoAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueEvoAccess(condition: Condition) extends UniqueEvoAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct evos.
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniqueEvoAccess 
	extends SingleRowModelAccess[Evo] with FilterableView[UniqueEvoAccess] 
		with DistinctModelAccess[Evo, Option[Evo], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the poke from which this evo originates. None if no evo (or value) was found.
	  */
	def fromId(implicit connection: Connection) = pullColumn(model.fromIdColumn).int
	
	/**
	  * Id of the poke to which this evo leads. None if no evo (or value) was found.
	  */
	def toId(implicit connection: Connection) = pullColumn(model.toIdColumn).int
	
	/**
	  * The level at which this evo is enabled. None if not level-based. None if no evo (or value) was found.
	  */
	def levelThreshold(implicit connection: Connection) = pullColumn(model.levelThresholdColumn).int
	
	/**
	  * Id of the item associated with this evo. None if no evo (or value) was found.
	  */
	def itemId(implicit connection: Connection) = pullColumn(model.itemIdColumn).int
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = EvoModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = EvoFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueEvoAccess = 
		new UniqueEvoAccess._UniqueEvoAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the from ids of the targeted evos
	  * @param newFromId A new from id to assign
	  * @return Whether any evo was affected
	  */
	def fromId_=(newFromId: Int)(implicit connection: Connection) = putColumn(model.fromIdColumn, newFromId)
	
	/**
	  * Updates the item ids of the targeted evos
	  * @param newItemId A new item id to assign
	  * @return Whether any evo was affected
	  */
	def itemId_=(newItemId: Int)(implicit connection: Connection) = putColumn(model.itemIdColumn, newItemId)
	
	/**
	  * Updates the level thresholds of the targeted evos
	  * @param newLevelThreshold A new level threshold to assign
	  * @return Whether any evo was affected
	  */
	def levelThreshold_=(newLevelThreshold: Int)(implicit connection: Connection) = 
		putColumn(model.levelThresholdColumn, newLevelThreshold)
	
	/**
	  * Updates the to ids of the targeted evos
	  * @param newToId A new to id to assign
	  * @return Whether any evo was affected
	  */
	def toId_=(newToId: Int)(implicit connection: Connection) = putColumn(model.toIdColumn, newToId)
}

