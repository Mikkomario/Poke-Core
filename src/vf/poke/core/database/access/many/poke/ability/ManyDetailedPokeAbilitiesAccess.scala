package vf.poke.core.database.access.many.poke.ability

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.DetailedPokeAbilityFactory
import vf.poke.core.database.model.game.AbilityModel
import vf.poke.core.model.combined.poke.DetailedPokeAbility

object ManyDetailedPokeAbilitiesAccess
{
	// NESTED	--------------------
	
	private class SubAccess(condition: Condition) extends ManyDetailedPokeAbilitiesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return multiple detailed poke abilities at a time
  * @author Mikko Hilpinen
  * @since 13.12.2023
  */
trait ManyDetailedPokeAbilitiesAccess 
	extends ManyPokeAbilitiesAccessLike[DetailedPokeAbility, ManyDetailedPokeAbilitiesAccess] 
		with ManyRowModelAccess[DetailedPokeAbility]
{
	// COMPUTED	--------------------
	
	/**
	  * game ids of the accessible abilities
	  */
	def abilityGameIds(implicit connection: Connection) = 
		pullColumn(abilityModel.gameIdColumn).map { v => v.getInt }
	
	/**
	  * in game indices of the accessible abilities
	  */
	def abilityInGameIndices(implicit connection: Connection) = 
		pullColumn(abilityModel.indexInGameColumn).map { v => v.getInt }
	
	/**
	  * names of the accessible abilities
	  */
	def abilityNames(implicit connection: Connection) = pullColumn(abilityModel.nameColumn)
		.flatMap { _.string }
	
	/**
	  * Model (factory) used for interacting the abilities associated with this detailed poke ability
	  */
	protected def abilityModel = AbilityModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = DetailedPokeAbilityFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyDetailedPokeAbilitiesAccess = 
		new ManyDetailedPokeAbilitiesAccess.SubAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the game ids of the targeted abilities
	  * @param newGameId A new game id to assign
	  * @return Whether any ability was affected
	  */
	def abilityGameIds_=(newGameId: Int)(implicit connection: Connection) = 
		putColumn(abilityModel.gameIdColumn, newGameId)
	
	/**
	  * Updates the in game indices of the targeted abilities
	  * @param newIndexInGame A new index in game to assign
	  * @return Whether any ability was affected
	  */
	def abilityInGameIndices_=(newIndexInGame: Int)(implicit connection: Connection) = 
		putColumn(abilityModel.indexInGameColumn, newIndexInGame)
	
	/**
	  * Updates the names of the targeted abilities
	  * @param newName A new name to assign
	  * @return Whether any ability was affected
	  */
	def abilityNames_=(newName: String)(implicit connection: Connection) = 
		putColumn(abilityModel.nameColumn, newName)
}

