package vf.poke.companion.model.partial.gameplay

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactory
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.{ModelConvertible, ModelLike, Property}
import utopia.flow.time.Now
import vf.poke.core.model.enumeration.PokeType

import java.time.Instant

object TypeGuessData extends FromModelFactory[TypeGuessData]
{
	// ATTRIBUTES	--------------------
	
	lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("runId", IntType, Vector("run_id")), 
			PropertyDeclaration("pokeId", IntType, Vector("poke_id")), PropertyDeclaration("guessedType", 
			IntType, Vector("guessed_type")), PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("deprecatedAfter", InstantType, Vector("deprecated_after"), 
			isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override def apply(model: ModelLike[Property]) = {
		schema.validate(model).toTry.flatMap { valid => 
			PokeType.fromValue(valid("guessedType")).map { guessedType => 
				TypeGuessData(valid("runId").getInt, valid("pokeId").getInt, guessedType, 
					valid("created").getInstant, valid("deprecatedAfter").instant)
			}
		}
	}
}

/**
  * Represents the user's guess at a poke's type
  * @param runId Id of the game run on which this guess was made
  * @param pokeId Id of the described poke
  * @param guessedType Poke type of this type guess
  * @param created Time when this type guess was added to the database
  * @param deprecatedAfter Time when this guess was replaced or when it became invalid
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class TypeGuessData(runId: Int, pokeId: Int, guessedType: PokeType, created: Instant = Now, 
	deprecatedAfter: Option[Instant] = None) 
	extends ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this type guess has already been deprecated
	  */
	def isDeprecated = deprecatedAfter.isDefined
	
	/**
	  * Whether this type guess is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("runId" -> runId, "pokeId" -> pokeId, "guessedType" -> guessedType.id, 
			"created" -> created, "deprecatedAfter" -> deprecatedAfter))
}

