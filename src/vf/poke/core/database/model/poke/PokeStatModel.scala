package vf.poke.core.database.model.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.core.database.factory.poke.PokeStatFactory
import vf.poke.core.model.enumeration.Stat
import vf.poke.core.model.partial.poke.PokeStatData
import vf.poke.core.model.stored.poke.PokeStat

/**
  * Used for constructing PokeStatModel instances and for inserting poke stats to the database
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object PokeStatModel extends DataInserter[PokeStatModel, PokeStat, PokeStatData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains poke stat poke id
	  */
	val pokeIdAttName = "pokeId"
	
	/**
	  * Name of the property that contains poke stat stat
	  */
	val statAttName = "statId"
	
	/**
	  * Name of the property that contains poke stat value
	  */
	val valueAttName = "value"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains poke stat poke id
	  */
	def pokeIdColumn = table(pokeIdAttName)
	
	/**
	  * Column that contains poke stat stat
	  */
	def statColumn = table(statAttName)
	
	/**
	  * Column that contains poke stat value
	  */
	def valueColumn = table(valueAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = PokeStatFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: PokeStatData) = apply(None, Some(data.pokeId), Some(data.stat.id), 
		Some(data.value))
	
	override protected def complete(id: Value, data: PokeStatData) = PokeStat(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param id A poke stat id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param pokeId Id of the described poke
	  * @return A model containing only the specified poke id
	  */
	def withPokeId(pokeId: Int) = apply(pokeId = Some(pokeId))
	
	/**
	  * @param stat Described stat / attribute
	  * @return A model containing only the specified stat
	  */
	def withStat(stat: Stat) = apply(stat = Some(stat.id))
	
	/**
	  * @param value Assigned value, between 10 and 255
	  * @return A model containing only the specified value
	  */
	def withValue(value: Int) = apply(value = Some(value))
}

/**
  * Used for interacting with PokeStats in the database
  * @param id poke stat database id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class PokeStatModel(id: Option[Int] = None, pokeId: Option[Int] = None, stat: Option[Int] = None, 
	value: Option[Int] = None) 
	extends StorableWithFactory[PokeStat]
{
	// IMPLEMENTED	--------------------
	
	override def factory = PokeStatModel.factory
	
	override def valueProperties = {
		import PokeStatModel._
		Vector("id" -> id, pokeIdAttName -> pokeId, statAttName -> stat, valueAttName -> value)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param pokeId Id of the described poke
	  * @return A new copy of this model with the specified poke id
	  */
	def withPokeId(pokeId: Int) = copy(pokeId = Some(pokeId))
	
	/**
	  * @param stat Described stat / attribute
	  * @return A new copy of this model with the specified stat
	  */
	def withStat(stat: Stat) = copy(stat = Some(stat.id))
	
	/**
	  * @param value Assigned value, between 10 and 255
	  * @return A new copy of this model with the specified value
	  */
	def withValue(value: Int) = copy(value = Some(value))
}

