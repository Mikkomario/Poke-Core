package vf.poke.core.database.access.many.poke.evo

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.EvoFactory
import vf.poke.core.database.model.poke.EvoModel
import vf.poke.core.model.stored.poke.Evo

object ManyEvosAccess
{
	// NESTED	--------------------
	
	private class ManyEvosSubView(condition: Condition) extends ManyEvosAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple evos at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait ManyEvosAccess extends ManyRowModelAccess[Evo] with FilterableView[ManyEvosAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * from ids of the accessible evos
	  */
	def fromIds(implicit connection: Connection) = pullColumn(model.fromIdColumn).map { v => v.getInt }
	
	/**
	  * to ids of the accessible evos
	  */
	def toIds(implicit connection: Connection) = pullColumn(model.toIdColumn).map { v => v.getInt }
	
	/**
	  * level thresholds of the accessible evos
	  */
	def levelThresholds(implicit connection: Connection) = 
		pullColumn(model.levelThresholdColumn).flatMap { v => v.int }
	
	/**
	  * item ids of the accessible evos
	  */
	def itemIds(implicit connection: Connection) = pullColumn(model.itemIdColumn).flatMap { v => v.int }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = EvoModel
	
	/**
	 * @param connection Implicit DB connection
	 * @return A map that contains the 'to' ids of each accessible evo mapped to their 'from' ids
	 */
	def toMap(implicit connection: Connection) =
		pullColumnMap(model.fromIdColumn, model.toIdColumn)
			.map { case (fromVal, toVal) => fromVal.getInt -> toVal.getInt }
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = EvoFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyEvosAccess = 
		new ManyEvosAccess.ManyEvosSubView(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	 * @param pokeId Id of the 'from' stage poke
	 * @return Access to evos from that poke
	 */
	def from(pokeId: Int) = filter(model.withFromId(pokeId).toCondition)
	/**
	 * @param pokeIds Ids of the targeted 'from' stage pokes
	 * @return Access to evos from those pokes
	 */
	def from(pokeIds: Iterable[Int]) = filter(model.fromIdColumn.in(pokeIds))
	
	/**
	  * Updates the from ids of the targeted evos
	  * @param newFromId A new from id to assign
	  * @return Whether any evo was affected
	  */
	def fromIds_=(newFromId: Int)(implicit connection: Connection) = putColumn(model.fromIdColumn, newFromId)
	
	/**
	  * Updates the item ids of the targeted evos
	  * @param newItemId A new item id to assign
	  * @return Whether any evo was affected
	  */
	def itemIds_=(newItemId: Int)(implicit connection: Connection) = putColumn(model.itemIdColumn, newItemId)
	
	/**
	  * Updates the level thresholds of the targeted evos
	  * @param newLevelThreshold A new level threshold to assign
	  * @return Whether any evo was affected
	  */
	def levelThresholds_=(newLevelThreshold: Int)(implicit connection: Connection) = 
		putColumn(model.levelThresholdColumn, newLevelThreshold)
	
	/**
	  * Updates the to ids of the targeted evos
	  * @param newToId A new to id to assign
	  * @return Whether any evo was affected
	  */
	def toIds_=(newToId: Int)(implicit connection: Connection) = putColumn(model.toIdColumn, newToId)
}

