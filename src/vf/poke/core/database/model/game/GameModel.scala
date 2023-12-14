package vf.poke.core.database.model.game

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.core.database.factory.game.GameFactory
import vf.poke.core.model.partial.game.GameData
import vf.poke.core.model.stored.game.Game

/**
  * Used for constructing GameModel instances and for inserting games to the database
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object GameModel extends DataInserter[GameModel, Game, GameData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains game name
	  */
	val nameAttName = "name"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains game name
	  */
	def nameColumn = table(nameAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = GameFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: GameData) = apply(None, data.name)
	
	override protected def complete(id: Value, data: GameData) = Game(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param id A game id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param name Name of this game
	  * @return A model containing only the specified name
	  */
	def withName(name: String) = apply(name = name)
}

/**
  * Used for interacting with Games in the database
  * @param id game database id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class GameModel(id: Option[Int] = None, name: String = "") extends StorableWithFactory[Game]
{
	// IMPLEMENTED	--------------------
	
	override def factory = GameModel.factory
	
	override def valueProperties = {
		import GameModel._
		Vector("id" -> id, nameAttName -> name)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param name Name of this game
	  * @return A new copy of this model with the specified name
	  */
	def withName(name: String) = copy(name = name)
}

