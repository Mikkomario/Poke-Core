package vf.poke.core.database.model.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.core.database.factory.poke.PokeAbilityFactory
import vf.poke.core.model.partial.poke.PokeAbilityData
import vf.poke.core.model.stored.poke.PokeAbility

/**
  * Used for constructing PokeAbilityModel instances and for inserting poke abilities to the database
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object PokeAbilityModel extends DataInserter[PokeAbilityModel, PokeAbility, PokeAbilityData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains poke ability poke id
	  */
	val pokeIdAttName = "pokeId"
	
	/**
	  * Name of the property that contains poke ability ability id
	  */
	val abilityIdAttName = "abilityId"
	
	/**
	  * Name of the property that contains poke ability is hidden
	  */
	val isHiddenAttName = "isHidden"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains poke ability poke id
	  */
	def pokeIdColumn = table(pokeIdAttName)
	
	/**
	  * Column that contains poke ability ability id
	  */
	def abilityIdColumn = table(abilityIdAttName)
	
	/**
	  * Column that contains poke ability is hidden
	  */
	def isHiddenColumn = table(isHiddenAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = PokeAbilityFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: PokeAbilityData) = 
		apply(None, Some(data.pokeId), Some(data.abilityId), Some(data.isHidden))
	
	override protected def complete(id: Value, data: PokeAbilityData) = PokeAbility(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param abilityId Id of the ability this poke may have
	  * @return A model containing only the specified ability id
	  */
	def withAbilityId(abilityId: Int) = apply(abilityId = Some(abilityId))
	
	/**
	  * @param id A poke ability id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param isHidden True if this is a hidden ability. Hidden abilities may only be acquired 
		with special measures.
	  * @return A model containing only the specified is hidden
	  */
	def withIsHidden(isHidden: Boolean) = apply(isHidden = Some(isHidden))
	
	/**
	  * @param pokeId Id of the poke that may have this ability
	  * @return A model containing only the specified poke id
	  */
	def withPokeId(pokeId: Int) = apply(pokeId = Some(pokeId))
}

/**
  * Used for interacting with PokeAbilities in the database
  * @param id poke ability database id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class PokeAbilityModel(id: Option[Int] = None, pokeId: Option[Int] = None, 
	abilityId: Option[Int] = None, isHidden: Option[Boolean] = None) 
	extends StorableWithFactory[PokeAbility]
{
	// IMPLEMENTED	--------------------
	
	override def factory = PokeAbilityModel.factory
	
	override def valueProperties = {
		import PokeAbilityModel._
		Vector("id" -> id, pokeIdAttName -> pokeId, abilityIdAttName -> abilityId, 
			isHiddenAttName -> isHidden)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param abilityId Id of the ability this poke may have
	  * @return A new copy of this model with the specified ability id
	  */
	def withAbilityId(abilityId: Int) = copy(abilityId = Some(abilityId))
	
	/**
	  * @param isHidden True if this is a hidden ability. Hidden abilities may only be acquired 
		with special measures.
	  * @return A new copy of this model with the specified is hidden
	  */
	def withIsHidden(isHidden: Boolean) = copy(isHidden = Some(isHidden))
	
	/**
	  * @param pokeId Id of the poke that may have this ability
	  * @return A new copy of this model with the specified poke id
	  */
	def withPokeId(pokeId: Int) = copy(pokeId = Some(pokeId))
}

