package vf.poke.core.database.model.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.core.database.factory.poke.PokeFactory
import vf.poke.core.model.enumeration.PokeType
import vf.poke.core.model.partial.poke.PokeData
import vf.poke.core.model.stored.poke.Poke

/**
  * Used for constructing PokeModel instances and for inserting pokes to the database
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object PokeModel extends DataInserter[PokeModel, Poke, PokeData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains poke randomization id
	  */
	val randomizationIdAttName = "randomizationId"
	
	/**
	  * Name of the property that contains poke number
	  */
	val numberAttName = "number"
	
	/**
	  * Name of the property that contains poke forme index
	  */
	val formeIndexAttName = "formeIndex"
	
	/**
	  * Name of the property that contains poke name
	  */
	val nameAttName = "name"
	
	/**
	  * Name of the property that contains poke primary type
	  */
	val primaryTypeAttName = "primaryTypeId"
	
	/**
	  * Name of the property that contains poke secondary type
	  */
	val secondaryTypeAttName = "secondaryTypeId"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains poke randomization id
	  */
	def randomizationIdColumn = table(randomizationIdAttName)
	
	/**
	  * Column that contains poke number
	  */
	def numberColumn = table(numberAttName)
	
	/**
	  * Column that contains poke forme index
	  */
	def formeIndexColumn = table(formeIndexAttName)
	
	/**
	  * Column that contains poke name
	  */
	def nameColumn = table(nameAttName)
	
	/**
	  * Column that contains poke primary type
	  */
	def primaryTypeColumn = table(primaryTypeAttName)
	
	/**
	  * Column that contains poke secondary type
	  */
	def secondaryTypeColumn = table(secondaryTypeAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = PokeFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: PokeData) = 
		apply(None, Some(data.randomizationId), Some(data.number), Some(data.formeIndex), data.name, 
			Some(data.primaryType.id), data.secondaryType.map { e => e.id })
	
	override protected def complete(id: Value, data: PokeData) = Poke(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param formeIndex 0-based index of the specific poke 
	  * "forme" represented by this instance. 0 is the default forme.
	  * @return A model containing only the specified forme index
	  */
	def withFormeIndex(formeIndex: Int) = apply(formeIndex = Some(formeIndex))
	
	/**
	  * @param id A poke id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param name Name of this poke in the english translation / game data
	  * @return A model containing only the specified name
	  */
	def withName(name: String) = apply(name = name)
	
	/**
	  * @param number The pokedex-number of this poke in the game in which it appears
	  * @return A model containing only the specified number
	  */
	def withNumber(number: Int) = apply(number = Some(number))
	
	/**
	  * @param primaryType The primary type of this poke
	  * @return A model containing only the specified primary type
	  */
	def withPrimaryType(primaryType: PokeType) = apply(primaryType = Some(primaryType.id))
	
	/**
	  * @param randomizationId The randomization in which this poke appears
	  * @return A model containing only the specified randomization id
	  */
	def withRandomizationId(randomizationId: Int) = apply(randomizationId = Some(randomizationId))
	
	/**
	  * @param secondaryType The secondary type of this poke. None if this poke only has one type
	  * @return A model containing only the specified secondary type
	  */
	def withSecondaryType(secondaryType: PokeType) = apply(secondaryType = Some(secondaryType.id))
}

/**
  * Used for interacting with Pokes in the database
  * @param id poke database id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class PokeModel(id: Option[Int] = None, randomizationId: Option[Int] = None, number: Option[Int] = None, 
	formeIndex: Option[Int] = None, name: String = "", primaryType: Option[Int] = None, 
	secondaryType: Option[Int] = None) 
	extends StorableWithFactory[Poke]
{
	// IMPLEMENTED	--------------------
	
	override def factory = PokeModel.factory
	
	override def valueProperties = {
		import PokeModel._
		Vector("id" -> id, randomizationIdAttName -> randomizationId, numberAttName -> number, 
			formeIndexAttName -> formeIndex, nameAttName -> name, primaryTypeAttName -> primaryType, 
			secondaryTypeAttName -> secondaryType)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param formeIndex 0-based index of the specific poke 
	  * "forme" represented by this instance. 0 is the default forme.
	  * @return A new copy of this model with the specified forme index
	  */
	def withFormeIndex(formeIndex: Int) = copy(formeIndex = Some(formeIndex))
	
	/**
	  * @param name Name of this poke in the english translation / game data
	  * @return A new copy of this model with the specified name
	  */
	def withName(name: String) = copy(name = name)
	
	/**
	  * @param number The pokedex-number of this poke in the game in which it appears
	  * @return A new copy of this model with the specified number
	  */
	def withNumber(number: Int) = copy(number = Some(number))
	
	/**
	  * @param primaryType The primary type of this poke
	  * @return A new copy of this model with the specified primary type
	  */
	def withPrimaryType(primaryType: PokeType) = copy(primaryType = Some(primaryType.id))
	
	/**
	  * @param randomizationId The randomization in which this poke appears
	  * @return A new copy of this model with the specified randomization id
	  */
	def withRandomizationId(randomizationId: Int) = copy(randomizationId = Some(randomizationId))
	
	/**
	  * @param secondaryType The secondary type of this poke. None if this poke only has one type
	  * @return A new copy of this model with the specified secondary type
	  */
	def withSecondaryType(secondaryType: PokeType) = copy(secondaryType = Some(secondaryType.id))
}

