package vf.poke.core.model.partial.randomization

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.BooleanType
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.poke.core.database.access.single.game.DbGame

import java.time.Instant

object RandomizationData extends FromModelFactoryWithSchema[RandomizationData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("gameId", IntType, Vector("game_id")), 
			PropertyDeclaration("created", InstantType, isOptional = true), PropertyDeclaration("isOriginal", 
			BooleanType, Vector("is_original"), false)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		RandomizationData(valid("gameId").getInt, valid("created").getInstant, valid("isOriginal").getBoolean)
}

/**
  * Represents a single, typically randomized, game version / ROM
  * @param gameId Id of the targeted game / ROM
  * @param created Time when this randomization was added to the database
  * @param isOriginal True if this represents an unmodified copy of the game
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class RandomizationData(gameId: Int, created: Instant = Now, isOriginal: Boolean = false) 
	extends ModelConvertible
{
	// COMPUTED ------------------------
	
	def gameAccess = DbGame(gameId)
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = Model(Vector("gameId" -> gameId, "created" -> created, "isOriginal" -> isOriginal))
}

