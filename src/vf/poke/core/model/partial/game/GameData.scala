package vf.poke.core.model.partial.game

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ModelConvertible

object GameData extends FromModelFactoryWithSchema[GameData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("name", StringType, isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = GameData(valid("name").getString)
}

/**
  * Represents a pokemon game entry
  * @param name Name of this game
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class GameData(name: String = "") extends ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = Model(Vector("name" -> name))
}

