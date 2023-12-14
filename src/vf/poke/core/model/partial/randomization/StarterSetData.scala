package vf.poke.core.model.partial.randomization

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.ModelConvertible

object StarterSetData extends FromModelFactoryWithSchema[StarterSetData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("randomizationId", IntType, Vector("randomization_id")), 
			PropertyDeclaration("placement", IntType)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		StarterSetData(valid("randomizationId").getInt, valid("placement").getInt)
}

/**
  * Represents a set of (3) starters that appears in a game
  * @param randomizationId Id of the randomization where these starters appear
  * @param placement Relative set index within the game, 0-based
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class StarterSetData(randomizationId: Int, placement: Int) extends ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = Model(Vector("randomizationId" -> randomizationId, "placement" -> placement))
}

