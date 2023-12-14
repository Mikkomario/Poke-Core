package vf.poke.core.model.partial.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactory
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration, Value}
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.{ModelConvertible, ModelLike, Property}
import vf.poke.core.model.cached.TypeSet
import vf.poke.core.model.enumeration.PokeType

object PokeData extends FromModelFactory[PokeData]
{
	// ATTRIBUTES	--------------------
	
	lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("randomizationId", IntType, Vector("randomization_id")), 
			PropertyDeclaration("number", IntType), PropertyDeclaration("formeIndex", IntType, 
			Vector("forme_index"), 0), PropertyDeclaration("name", StringType), 
			PropertyDeclaration("primaryType", IntType, Vector("primary_type")), 
			PropertyDeclaration("secondaryType", IntType, Vector("secondary_type"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override def apply(model: ModelLike[Property]) = {
		schema.validate(model).toTry.flatMap { valid => 
			PokeType.fromValue(valid("primaryType")).map { primaryType => 
				PokeData(valid("randomizationId").getInt, valid("number").getInt, valid("formeIndex").getInt, 
					valid("name").getString, primaryType, 
					valid("secondaryType").int.flatMap(PokeType.findForId))
			}
		}
	}
}

/**
  * Represents a randomized poke from one of the games
  * @param randomizationId The randomization in which this poke appears
  * @param number The pokedex-number of this poke in the game in which it appears
  * @param formeIndex 0-based index of the specific poke 
  * "forme" represented by this instance. 0 is the default forme.
  * @param name Name of this poke in the english translation / game data
  * @param primaryType The primary type of this poke
  * @param secondaryType The secondary type of this poke. None if this poke only has one type
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class PokeData(randomizationId: Int, number: Int, formeIndex: Int, name: String, primaryType: PokeType, 
	secondaryType: Option[PokeType] = None) 
	extends ModelConvertible
{
	// ATTRIBUTES   --------------------
	
	lazy val types = TypeSet(primaryType, secondaryType)
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("randomizationId" -> randomizationId, "number" -> number, "formeIndex" -> formeIndex, 
			"name" -> name, "primaryType" -> primaryType.id, 
			"secondaryType" -> secondaryType.map[Value] { e => e.id }.getOrElse(Value.empty)))
}

