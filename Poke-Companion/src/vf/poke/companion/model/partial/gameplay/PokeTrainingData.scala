package vf.poke.companion.model.partial.gameplay

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now

import java.time.Instant

object PokeTrainingData extends FromModelFactoryWithSchema[PokeTrainingData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("runId", IntType, Vector("run_id")), 
			PropertyDeclaration("pokeId", IntType, Vector("poke_id")), PropertyDeclaration("level", IntType), 
			PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("deprecatedAfter", InstantType, Vector("deprecated_after"), 
			isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		PokeTrainingData(valid("runId").getInt, valid("pokeId").getInt, valid("level").getInt, 
			valid("created").getInstant, valid("deprecatedAfter").instant)
}

/**
  * Represents an event / recording of a trained poke reaching a specific level
  * @param runId Id of the run on which this event occurred
  * @param pokeId Id of the trained poke
  * @param level Reached level
  * @param created Time when this poke training was added to the database
  * @param deprecatedAfter Time when this event was replaced with a more recent recording
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class PokeTrainingData(runId: Int, pokeId: Int, level: Int, created: Instant = Now, 
	deprecatedAfter: Option[Instant] = None) 
	extends ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this poke training has already been deprecated
	  */
	def isDeprecated = deprecatedAfter.isDefined
	
	/**
	  * Whether this poke training is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("runId" -> runId, "pokeId" -> pokeId, "level" -> level, "created" -> created, 
			"deprecatedAfter" -> deprecatedAfter))
}

