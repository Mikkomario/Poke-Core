package vf.poke.core.model.partial.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.ModelConvertible

object MoveLearnData extends FromModelFactoryWithSchema[MoveLearnData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("pokeId", IntType, Vector("poke_id")), 
			PropertyDeclaration("moveId", IntType, Vector("move_id")), PropertyDeclaration("level", IntType)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		MoveLearnData(valid("pokeId").getInt, valid("moveId").getInt, valid("level").getInt)
}

/**
  * Represents a poke's ability to learn a move
  * @param pokeId Id of the poke that learns the move
  * @param moveId Id of the move learnt
  * @param level Level at which this move is learnt, if applicable.
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class MoveLearnData(pokeId: Int, moveId: Int, level: Int) extends ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = Model(Vector("pokeId" -> pokeId, "moveId" -> moveId, "level" -> level))
}

