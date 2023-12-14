package vf.poke.companion.model.partial.gameplay

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now

import java.time.Instant

object PokeCaptureData extends FromModelFactoryWithSchema[PokeCaptureData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("runId", IntType, Vector("run_id")), 
			PropertyDeclaration("pokeId", IntType, Vector("poke_id")), PropertyDeclaration("level", IntType), 
			PropertyDeclaration("created", InstantType, isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		PokeCaptureData(valid("runId").getInt, valid("pokeId").getInt, valid("level").getInt, 
			valid("created").getInstant)
}

/**
  * Represents the event of capturing some poke
  * @param runId Id of the game run on which this capture was made
  * @param pokeId Id of the captured poke
  * @param level Level at which this capture was made
  * @param created Time when this poke capture was added to the database
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class PokeCaptureData(runId: Int, pokeId: Int, level: Int, created: Instant = Now) 
	extends ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("runId" -> runId, "pokeId" -> pokeId, "level" -> level, "created" -> created))
}

