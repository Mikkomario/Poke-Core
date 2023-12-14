package vf.poke.core.model.partial.randomization

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.ModelConvertible

object StarterAssignmentData extends FromModelFactoryWithSchema[StarterAssignmentData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("setId", IntType, Vector("set_id")), 
			PropertyDeclaration("pokeId", IntType, Vector("poke_id")), PropertyDeclaration("placement", 
			IntType)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		StarterAssignmentData(valid("setId").getInt, valid("pokeId").getInt, valid("placement").getInt)
}

/**
  * Represents a starter poke-assignment in a game
  * @param setId Id of the starter set to which this starter belongs
  * @param pokeId Id of the poke that appears as a starter
  * @param placement A zero-based index that shows where this starter appears relative to the others.
  *  The following index is typically strong against the previous index.
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class StarterAssignmentData(setId: Int, pokeId: Int, placement: Int) extends ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = Model(Vector("setId" -> setId, "pokeId" -> pokeId, "placement" -> placement))
}

