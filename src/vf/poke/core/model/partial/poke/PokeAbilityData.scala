package vf.poke.core.model.partial.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.BooleanType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.ModelConvertible

object PokeAbilityData extends FromModelFactoryWithSchema[PokeAbilityData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("pokeId", IntType, Vector("poke_id")), 
			PropertyDeclaration("abilityId", IntType, Vector("ability_id")), PropertyDeclaration("isHidden", 
			BooleanType, Vector("is_hidden"), false)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		PokeAbilityData(valid("pokeId").getInt, valid("abilityId").getInt, valid("isHidden").getBoolean)
}

/**
  * Represents an ability that's possible for a poke to have
  * @param pokeId Id of the poke that may have this ability
  * @param abilityId Id of the ability this poke may have
  * @param isHidden True if this is a hidden ability. Hidden abilities may only be acquired 
	with special measures.
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class PokeAbilityData(pokeId: Int, abilityId: Int, isHidden: Boolean = false) extends ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = Model(Vector("pokeId" -> pokeId, "abilityId" -> abilityId, "isHidden" -> isHidden))
}

