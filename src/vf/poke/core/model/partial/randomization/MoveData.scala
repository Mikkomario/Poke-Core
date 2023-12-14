package vf.poke.core.model.partial.randomization

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactory
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration, Value}
import utopia.flow.generic.model.mutable.DataType.DoubleType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.{ModelConvertible, ModelLike, Property}
import vf.poke.core.model.enumeration.CriticalRate.Normal
import vf.poke.core.model.enumeration.{CriticalRate, MoveCategory, PokeType}

object MoveData extends FromModelFactory[MoveData]
{
	// ATTRIBUTES	--------------------
	
	lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("randomizationId", IntType, Vector("randomization_id")), 
			PropertyDeclaration("indexInGame", IntType, Vector("index_in_game")), PropertyDeclaration("name", 
			StringType, isOptional = true), PropertyDeclaration("moveType", IntType, Vector("move_type")), 
			PropertyDeclaration("category", IntType, isOptional = true), PropertyDeclaration("damage", 
			IntType), PropertyDeclaration("hitCount", DoubleType, Vector("hit_count"), 1.0), 
			PropertyDeclaration("hitRatio", DoubleType, Vector("hit_ratio"), 100.0), 
			PropertyDeclaration("criticalRate", IntType, Vector("critical_rate"), Normal.id)))
	
	
	// IMPLEMENTED	--------------------
	
	override def apply(model: ModelLike[Property]) = {
		schema.validate(model).toTry.flatMap { valid => 
			PokeType.fromValue(valid("moveType")).map { moveType => 
				MoveData(valid("randomizationId").getInt, valid("indexInGame").getInt, 
					valid("name").getString, moveType, valid("category").int.flatMap(MoveCategory.findForId), 
					valid("damage").getInt, valid("hitCount").getDouble, valid("hitRatio").getDouble, 
					CriticalRate.fromValue(valid("criticalRate")))
			}
		}
	}
}

/**
  * Represents a possibly randomized move
  * @param randomizationId Id of the randomization where these details apply
  * @param indexInGame Index of this move in the game data
  * @param name Name of this move in the game
  * @param moveType Type of this move
  * @param category Category of this move, whether physical or special. None if neither.
  * @param damage The amount of damage inflicted by this move, in game units
  * @param hitCount The (average) hit count of this move
  * @param hitRatio The ratio of how often this move hits, 
	where 100 is 100% hit without accuracy or evasion mods
  * @param criticalRate Rate of critical hits applied to this move
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class MoveData(randomizationId: Int, indexInGame: Int, name: String, moveType: PokeType, 
	category: Option[MoveCategory], damage: Int, hitCount: Double = 1.0, hitRatio: Double = 100.0, 
	criticalRate: CriticalRate = Normal) 
	extends ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("randomizationId" -> randomizationId, "indexInGame" -> indexInGame, "name" -> name, 
			"moveType" -> moveType.id, 
			"category" -> category.map[Value] { e => e.id }.getOrElse(Value.empty), "damage" -> damage, 
			"hitCount" -> hitCount, "hitRatio" -> hitRatio, "criticalRate" -> criticalRate.id))
}

