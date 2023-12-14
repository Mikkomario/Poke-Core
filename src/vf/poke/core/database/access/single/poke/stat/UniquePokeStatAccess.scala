package vf.poke.core.database.access.single.poke.stat

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.PokeStatFactory
import vf.poke.core.database.model.poke.PokeStatModel
import vf.poke.core.model.enumeration.Stat
import vf.poke.core.model.stored.poke.PokeStat

object UniquePokeStatAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniquePokeStatAccess = new _UniquePokeStatAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniquePokeStatAccess(condition: Condition) extends UniquePokeStatAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct poke stats.
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniquePokeStatAccess 
	extends SingleRowModelAccess[PokeStat] with FilterableView[UniquePokeStatAccess] 
		with DistinctModelAccess[PokeStat, Option[PokeStat], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the described poke. None if no poke stat (or value) was found.
	  */
	def pokeId(implicit connection: Connection) = pullColumn(model.pokeIdColumn).int
	
	/**
	  * Described stat / attribute. None if no poke stat (or value) was found.
	  */
	def stat(implicit connection: Connection) = pullColumn(model.statColumn).int.flatMap(Stat.findForId)
	
	/**
	  * Assigned value, between 10 and 255. None if no poke stat (or value) was found.
	  */
	def value(implicit connection: Connection) = pullColumn(model.valueColumn).int
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = PokeStatModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PokeStatFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniquePokeStatAccess = 
		new UniquePokeStatAccess._UniquePokeStatAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the poke ids of the targeted poke stats
	  * @param newPokeId A new poke id to assign
	  * @return Whether any poke stat was affected
	  */
	def pokeId_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
	
	/**
	  * Updates the stats of the targeted poke stats
	  * @param newStat A new stat to assign
	  * @return Whether any poke stat was affected
	  */
	def stat_=(newStat: Stat)(implicit connection: Connection) = putColumn(model.statColumn, newStat.id)
	
	/**
	  * Updates the values of the targeted poke stats
	  * @param newValue A new value to assign
	  * @return Whether any poke stat was affected
	  */
	def value_=(newValue: Int)(implicit connection: Connection) = putColumn(model.valueColumn, newValue)
}

