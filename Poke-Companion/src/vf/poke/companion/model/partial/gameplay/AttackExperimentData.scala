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

object AttackExperimentData extends FromModelFactory[AttackExperimentData]
{
	// ATTRIBUTES	--------------------
	
	lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("runId", IntType, Vector("run_id")), 
			PropertyDeclaration("opponentId", IntType, Vector("opponent_id")), 
			PropertyDeclaration("attackType", IntType, Vector("attack_type")), PropertyDeclaration("created", 
			InstantType, isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override def apply(model: ModelLike[Property]) = {
		schema.validate(model).toTry.flatMap { valid => 
			PokeType.fromValue(valid("attackType")).map { attackType => 
				AttackExperimentData(valid("runId").getInt, valid("opponentId").getInt, attackType, 
					valid("created").getInstant)
			}
		}
	}
}

/**
  * Represents an attempt to attack an opposing poke 
  * with some damage type. Used for revealing information about the opponent's typing.
  * @param runId Id of the game run on which this attempt was made
  * @param opponentId Id of the opposing poke
  * @param attackType Type of attack used against the poke
  * @param created Time when this attack experiment was added to the database
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class AttackExperimentData(runId: Int, opponentId: Int, attackType: PokeType, created: Instant = Now) 
	extends ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("runId" -> runId, "opponentId" -> opponentId, "attackType" -> attackType.id, 
			"created" -> created))
}

