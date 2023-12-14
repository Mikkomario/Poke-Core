package vf.poke.core.model.partial.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.ModelConvertible

object EvoData extends FromModelFactoryWithSchema[EvoData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("fromId", IntType, Vector("from_id")), 
			PropertyDeclaration("toId", IntType, Vector("to_id")), PropertyDeclaration("levelThreshold", 
			IntType, Vector("level_threshold"), isOptional = true), PropertyDeclaration("itemId", IntType, 
			Vector("item_id"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		EvoData(valid("fromId").getInt, valid("toId").getInt, valid("levelThreshold").int, 
			valid("itemId").int)
}

/**
  * Represents a permanent change from one poke form to another
  * @param fromId Id of the poke from which this evo originates
  * @param toId Id of the poke to which this evo leads
  * @param levelThreshold The level at which this evo is enabled. None if not level-based
  * @param itemId Id of the item associated with this evo
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class EvoData(fromId: Int, toId: Int, levelThreshold: Option[Int] = None, itemId: Option[Int] = None) 
	extends ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("fromId" -> fromId, "toId" -> toId, "levelThreshold" -> levelThreshold, 
			"itemId" -> itemId))
}

