package vf.poke.core.model.partial.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactory
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.{ModelConvertible, ModelLike, Property}
import vf.poke.core.model.enumeration.Stat

object PokeStatData extends FromModelFactory[PokeStatData]
{
	// ATTRIBUTES	--------------------
	
	lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("pokeId", IntType, Vector("poke_id")), 
			PropertyDeclaration("stat", IntType), PropertyDeclaration("value", IntType)))
	
	
	// IMPLEMENTED	--------------------
	
	override def apply(model: ModelLike[Property]) = {
		schema.validate(model).toTry.flatMap { valid => 
			Stat.fromValue(valid("stat")).map { stat => 
				PokeStatData(valid("pokeId").getInt, stat, valid("value").getInt)
			}
		}
	}
}

/**
  * Represents a (base) stat assigned to a poke
  * @param pokeId Id of the described poke
  * @param stat Described stat / attribute
  * @param value Assigned value, between 10 and 255
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class PokeStatData(pokeId: Int, stat: Stat, value: Int) extends ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = Model(Vector("pokeId" -> pokeId, "stat" -> stat.id, "value" -> value))
}

