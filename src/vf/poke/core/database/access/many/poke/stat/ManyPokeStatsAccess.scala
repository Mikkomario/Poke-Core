package vf.poke.core.database.access.many.poke.stat

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.PokeStatFactory
import vf.poke.core.database.model.poke.{PokeModel, PokeStatModel}
import vf.poke.core.model.enumeration.Stat
import vf.poke.core.model.stored.poke.PokeStat

object ManyPokeStatsAccess
{
	// NESTED	--------------------
	
	private class ManyPokeStatsSubView(condition: Condition) extends ManyPokeStatsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple poke stats at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait ManyPokeStatsAccess 
	extends ManyRowModelAccess[PokeStat] with FilterableView[ManyPokeStatsAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * poke ids of the accessible poke stats
	  */
	def pokeIds(implicit connection: Connection) = pullColumn(model.pokeIdColumn).map { v => v.getInt }
	
	/**
	  * stats of the accessible poke stats
	  */
	def stats(implicit connection: Connection) = 
		pullColumn(model.statColumn).map { v => v.getInt }.flatMap(Stat.findForId)
	
	/**
	  * values of the accessible poke stats
	  */
	def values(implicit connection: Connection) = pullColumn(model.valueColumn).map { v => v.getInt }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = PokeStatModel
	
	protected def pokeModel = PokeModel
	
	/**
	 * @param connection Implicit DB connection
	 * @return A map where stats are keys and stat-values are values
	 */
	def toMap(implicit connection: Connection) =
		pullColumnMap(model.statColumn, model.valueColumn).flatMap { case (statVal, valueVal) =>
			Stat.fromValue(statVal).toOption.map { _ -> valueVal.getInt }
		}
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PokeStatFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyPokeStatsAccess = 
		new ManyPokeStatsAccess.ManyPokeStatsSubView(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	 * @param pokeId Id of the targeted poke
	 * @return Access to that poke's stats
	 */
	def ofPoke(pokeId: Int) = filter(model.withPokeId(pokeId).toCondition)
	/**
	 * @param pokeIds Ids of the targeted pokes
	 * @return Access to stats concerning those pokes
	 */
	def ofPokes(pokeIds: Iterable[Int]) = filter(model.pokeIdColumn.in(pokeIds))
	
	/**
	 * Finds all accessible stats that apply to the specified randomization
	 * @param randomizationId Id of the targeted randomization
	 * @param connection Implicit DB connection
	 * @return Accessible stats in that randomization
	 */
	def findForRandomization(randomizationId: Int)(implicit connection: Connection) =
		find(pokeModel.withRandomizationId(randomizationId).toCondition, joins = Vector(pokeModel.table))
	
	/**
	  * Updates the poke ids of the targeted poke stats
	  * @param newPokeId A new poke id to assign
	  * @return Whether any poke stat was affected
	  */
	def pokeIds_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
	
	/**
	  * Updates the stats of the targeted poke stats
	  * @param newStat A new stat to assign
	  * @return Whether any poke stat was affected
	  */
	def stats_=(newStat: Stat)(implicit connection: Connection) = putColumn(model.statColumn, newStat.id)
	
	/**
	  * Updates the values of the targeted poke stats
	  * @param newValue A new value to assign
	  * @return Whether any poke stat was affected
	  */
	def values_=(newValue: Int)(implicit connection: Connection) = putColumn(model.valueColumn, newValue)
}

