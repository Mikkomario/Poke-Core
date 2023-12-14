package vf.poke.core.database.access.single.poke.ability

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.DetailedPokeAbilityFactory
import vf.poke.core.database.model.game.AbilityModel
import vf.poke.core.model.combined.poke.DetailedPokeAbility

object UniqueDetailedPokeAbilityAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueDetailedPokeAbilityAccess = 
		new _UniqueDetailedPokeAbilityAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueDetailedPokeAbilityAccess(condition: Condition)
		 extends UniqueDetailedPokeAbilityAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return distinct detailed poke abilities
  * @author Mikko Hilpinen
  * @since 13.12.2023, v1.0
  */
trait UniqueDetailedPokeAbilityAccess 
	extends UniquePokeAbilityAccessLike[DetailedPokeAbility] with SingleRowModelAccess[DetailedPokeAbility] 
		with FilterableView[UniqueDetailedPokeAbilityAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the game in which this ability appears. None if no ability (or value) was found.
	  */
	def abilityGameId(implicit connection: Connection) = pullColumn(abilityModel.gameIdColumn).int
	
	/**
	  * Index of this ability in the game. None if no ability (or value) was found.
	  */
	def abilityIndexInGame(implicit connection: Connection) = pullColumn(abilityModel.indexInGameColumn).int
	
	/**
	  * Name of this ability in the game. None if no ability (or value) was found.
	  */
	def abilityName(implicit connection: Connection) = pullColumn(abilityModel.nameColumn).getString
	
	/**
	  * A database model (factory) used for interacting with the linked ability
	  */
	protected def abilityModel = AbilityModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = DetailedPokeAbilityFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueDetailedPokeAbilityAccess = 
		new UniqueDetailedPokeAbilityAccess._UniqueDetailedPokeAbilityAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the game ids of the targeted abilities
	  * @param newGameId A new game id to assign
	  * @return Whether any ability was affected
	  */
	def abilityGameId_=(newGameId: Int)(implicit connection: Connection) = 
		putColumn(abilityModel.gameIdColumn, newGameId)
	
	/**
	  * Updates the in game indices of the targeted abilities
	  * @param newIndexInGame A new index in game to assign
	  * @return Whether any ability was affected
	  */
	def abilityIndexInGame_=(newIndexInGame: Int)(implicit connection: Connection) = 
		putColumn(abilityModel.indexInGameColumn, newIndexInGame)
	
	/**
	  * Updates the names of the targeted abilities
	  * @param newName A new name to assign
	  * @return Whether any ability was affected
	  */
	def abilityName_=(newName: String)(implicit connection: Connection) = 
		putColumn(abilityModel.nameColumn, newName)
}

