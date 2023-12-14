package vf.poke.core.database.model.game

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.core.database.factory.game.AbilityFactory
import vf.poke.core.model.partial.game.AbilityData
import vf.poke.core.model.stored.game.Ability

/**
  * Used for constructing AbilityModel instances and for inserting abilities to the database
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object AbilityModel extends DataInserter[AbilityModel, Ability, AbilityData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains ability game id
	  */
	val gameIdAttName = "gameId"
	
	/**
	  * Name of the property that contains ability index in game
	  */
	val indexInGameAttName = "indexInGame"
	
	/**
	  * Name of the property that contains ability name
	  */
	val nameAttName = "name"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains ability game id
	  */
	def gameIdColumn = table(gameIdAttName)
	
	/**
	  * Column that contains ability index in game
	  */
	def indexInGameColumn = table(indexInGameAttName)
	
	/**
	  * Column that contains ability name
	  */
	def nameColumn = table(nameAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = AbilityFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: AbilityData) = apply(None, Some(data.gameId), Some(data.indexInGame), data.name)
	
	override protected def complete(id: Value, data: AbilityData) = Ability(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param gameId Id of the game in which this ability appears
	  * @return A model containing only the specified game id
	  */
	def withGameId(gameId: Int) = apply(gameId = Some(gameId))
	
	/**
	  * @param id A ability id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param indexInGame Index of this ability in the game
	  * @return A model containing only the specified index in game
	  */
	def withIndexInGame(indexInGame: Int) = apply(indexInGame = Some(indexInGame))
	
	/**
	  * @param name Name of this ability in the game
	  * @return A model containing only the specified name
	  */
	def withName(name: String) = apply(name = name)
}

/**
  * Used for interacting with Abilities in the database
  * @param id ability database id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class AbilityModel(id: Option[Int] = None, gameId: Option[Int] = None, indexInGame: Option[Int] = None, 
	name: String = "") 
	extends StorableWithFactory[Ability]
{
	// IMPLEMENTED	--------------------
	
	override def factory = AbilityModel.factory
	
	override def valueProperties = {
		import AbilityModel._
		Vector("id" -> id, gameIdAttName -> gameId, indexInGameAttName -> indexInGame, nameAttName -> name)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param gameId Id of the game in which this ability appears
	  * @return A new copy of this model with the specified game id
	  */
	def withGameId(gameId: Int) = copy(gameId = Some(gameId))
	
	/**
	  * @param indexInGame Index of this ability in the game
	  * @return A new copy of this model with the specified index in game
	  */
	def withIndexInGame(indexInGame: Int) = copy(indexInGame = Some(indexInGame))
	
	/**
	  * @param name Name of this ability in the game
	  * @return A new copy of this model with the specified name
	  */
	def withName(name: String) = copy(name = name)
}

