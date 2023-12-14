package vf.poke.core.database

import utopia.vault.database.Tables
import utopia.vault.model.immutable.Table
import vf.poke.core.util.Common._

/**
  * Used for accessing the database tables introduced in this project
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object PokeTables extends Tables(cPool)
{
	// ATTRIBUTES   ----------------
	
	private val dbName = "poke_db"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Table that contains abilities (Represents a special ability that a poke can have)
	  */
	def ability = apply("ability")
	
	/**
	  * Table that contains battle encounters (Represents a scripted encounter 
		with a poke in a trainer battle)
	  */
	def battleEncounter = apply("battle_encounter")
	
	/**
	  * Table that contains evos (Represents a permanent change from one poke form to another)
	  */
	def evo = apply("evo")
	
	/**
	  * Table that contains evo moves (Represents a poke's ability to learn a move when evolving)
	  */
	def evoMove = apply("evo_move")
	
	/**
	  * Table that contains games (Represents a pokemon game entry)
	  */
	def game = apply("game")
	
	/**
	  * Table that contains items (Represents an item in a game)
	  */
	def item = apply("item")
	
	/**
	  * Table that contains moves (Represents a possibly randomized move)
	  */
	def move = apply("move")
	
	/**
	  * Table that contains move learns (Represents a poke's ability to learn a move)
	  */
	def moveLearn = apply("move_learn")
	
	/**
	  * Table that contains pokes (Represents a randomized poke from one of the games)
	  */
	def poke = apply("poke")
	
	/**
	  * Table that contains poke abilities (Represents an ability that's possible for a poke to have)
	  */
	def pokeAbility = apply("poke_ability")
	
	/**
	  * Table that contains poke stats (Represents a (base) stat assigned to a poke)
	  */
	def pokeStat = apply("poke_stat")
	
	/**
	  * Table that contains randomizations (Represents a single, typically randomized, game version / ROM)
	  */
	def randomization = apply("randomization")
	
	/**
	  * Table that contains starter assignments (Represents a starter poke-assignment in a game)
	  */
	def starterAssignment = apply("starter_assignment")
	
	/**
	  * Table that contains starter sets (Represents a set of (3) starters that appears in a game)
	  */
	def starterSet = apply("starter_set")
	
	/**
	  * Table that contains wild encounters (Represents a random encounter with a wild poke)
	  */
	def wildEncounter = apply("wild_encounter")
	
	
	// OTHER	--------------------
	
	def apply(tableName: String): Table = apply(dbName, tableName)
}

