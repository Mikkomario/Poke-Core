package vf.poke.core.model.partial.game

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ModelConvertible

object ItemData extends FromModelFactoryWithSchema[ItemData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("gameId", IntType, Vector("game_id")), 
			PropertyDeclaration("indexInGame", IntType, Vector("index_in_game")), PropertyDeclaration("name", 
			StringType, isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		ItemData(valid("gameId").getInt, valid("indexInGame").getInt, valid("name").getString)
}

/**
  * Represents an item in a game
  * @param gameId Id of the game in which this item appears
  * @param indexInGame Index of this item within the game
  * @param name Name of this item in the game
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class ItemData(gameId: Int, indexInGame: Int, name: String = "") extends ModelConvertible
{
	// IMPLEMENTED	--------------------
	
	override def toModel = Model(Vector("gameId" -> gameId, "indexInGame" -> indexInGame, "name" -> name))
}

