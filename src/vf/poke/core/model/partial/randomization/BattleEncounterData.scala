package vf.poke.core.model.partial.randomization

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.ModelConvertible

object BattleEncounterData extends FromModelFactoryWithSchema[BattleEncounterData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("randomizationId", IntType, Vector("randomization_id")), 
			PropertyDeclaration("pokeId", IntType, Vector("poke_id")), PropertyDeclaration("level", IntType), 
			PropertyDeclaration("numberOfEncounters", IntType, Vector("number_of_encounters"), 1)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		BattleEncounterData(valid("randomizationId").getInt, valid("pokeId").getInt, valid("level").getInt, 
			valid("numberOfEncounters").getInt)
}

/**
  * Represents a scripted encounter with a poke in a trainer battle
  * @param randomizationId Id of the randomization where this encounter applies
  * @param pokeId Id of the encountered poke
  * @param level Level at which this poke is encountered
  * @param numberOfEncounters Number of individual encounters represented by this instance
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class BattleEncounterData(randomizationId: Int, pokeId: Int, level: Int, numberOfEncounters: Int = 1) 
	extends ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("randomizationId" -> randomizationId, "pokeId" -> pokeId, "level" -> level, 
			"numberOfEncounters" -> numberOfEncounters))
}

