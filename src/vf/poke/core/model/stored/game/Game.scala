package vf.poke.core.model.stored.game

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.core.database.access.single.game.DbSingleGame
import vf.poke.core.model.partial.game.GameData

/**
  * Represents a game that has already been stored in the database
  * @param id id of this game in the database
  * @param data Wrapped game data
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class Game(id: Int, data: GameData) extends StoredModelConvertible[GameData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this game in the database
	  */
	def access = DbSingleGame(id)
}

