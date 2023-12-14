package vf.poke.core.model.partial.randomization

import utopia.flow.collection.immutable.range.NumericSpan
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration, Value}
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.mutable.DataType.PairType
import utopia.flow.generic.model.template.ModelConvertible

object WildEncounterData extends FromModelFactoryWithSchema[WildEncounterData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("randomizationId", IntType, Vector("randomization_id")), 
			PropertyDeclaration("zoneIndex", IntType, Vector("zone_index")), PropertyDeclaration("pokeId", 
			IntType, Vector("poke_id")), PropertyDeclaration("levelRange", PairType, Vector("level_range", 
			"maxLevel", "max_level", "minLevel", "min_level")), PropertyDeclaration("numberOfEncounters", 
			IntType, Vector("number_of_encounters"), 1)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		WildEncounterData(valid("randomizationId").getInt, valid("zoneIndex").getInt, valid("pokeId").getInt, 
			NumericSpan(valid("levelRange").getPair.map { v => v.getInt }), 
			valid("numberOfEncounters").getInt)
}

/**
  * Represents a random encounter with a wild poke
  * @param randomizationId Randomization in which this encounter applies
  * @param zoneIndex Index of the zone / map in which this encounter applies
  * @param pokeId Id of the poke that may be encountered
  * @param levelRange Range of levels that are possible for this encounter
  * @param numberOfEncounters Number of individual encounters represented by this instance
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class WildEncounterData(randomizationId: Int, zoneIndex: Int, pokeId: Int, levelRange: NumericSpan[Int], 
	numberOfEncounters: Int = 1) 
	extends ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("randomizationId" -> randomizationId, "zoneIndex" -> zoneIndex, "pokeId" -> pokeId, 
			"levelRange" -> levelRange.ends.map[Value] { v => v }, "numberOfEncounters" -> numberOfEncounters))
}

