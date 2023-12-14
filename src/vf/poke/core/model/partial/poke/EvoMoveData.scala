package vf.poke.core.model.partial.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.ModelConvertible

object EvoMoveData extends FromModelFactoryWithSchema[EvoMoveData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("pokeId", IntType, Vector("poke_id")), 
			PropertyDeclaration("moveId", IntType, Vector("move_id"))))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		EvoMoveData(valid("pokeId").getInt, valid("moveId").getInt)
}

/**
  * Represents a poke's ability to learn a move when evolving
  * @param pokeId Id of the poke (form) learning this move
  * @param moveId id of the move learnt
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class EvoMoveData(pokeId: Int, moveId: Int) extends ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = Model(Vector("pokeId" -> pokeId, "moveId" -> moveId))
}

