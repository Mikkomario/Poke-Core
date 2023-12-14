package vf.poke.companion.model.partial.gameplay

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now

import java.time.Instant

object RunData extends FromModelFactoryWithSchema[RunData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("name", StringType, isOptional = true), 
			PropertyDeclaration("randomizationId", IntType, Vector("randomization_id")), 
			PropertyDeclaration("created", InstantType, isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		RunData(valid("name").getString, valid("randomizationId").getInt, valid("created").getInstant)
}

/**
  * Represents a gameplay run of a randomized game version
  * @param name Name of this run, for example consisting of the player name
  * @param randomizationId Id of the randomization that's applied to this game run
  * @param created Time when this run was added to the database
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class RunData(name: String, randomizationId: Int, created: Instant = Now) extends ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("name" -> name, "randomizationId" -> randomizationId, "created" -> created))
}

