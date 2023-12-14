package vf.poke.companion.database

import utopia.vault.model.immutable.Table
import vf.poke.core.database.PokeTables

/**
  * Used for accessing the database tables introduced in this project
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object PokeCompanionTables
{
	// COMPUTED	--------------------
	
	/**
	  * Table that contains attack experiments (Represents an attempt to attack an opposing poke 
	  * with some damage type. Used for revealing information about the opponent's typing.)
	  */
	def attackExperiment = apply("attack_experiment")
	
	/**
	  * Table that contains poke captures (Represents the event of capturing some poke)
	  */
	def pokeCapture = apply("poke_capture")
	
	/**
	  * Table that contains poke trainings (Represents an event / 
	  * recording of a trained poke reaching a specific level)
	  */
	def pokeTraining = apply("poke_training")
	
	/**
	  * Table that contains runs (Represents a gameplay run of a randomized game version)
	  */
	def run = apply("run")
	
	/**
	  * Table that contains type guessses (Represents the user's guess at a poke's type)
	  */
	def typeGuess = apply("type_guess")
	
	
	// OTHER	--------------------
	
	private def apply(tableName: String): Table = PokeTables(tableName)
}

