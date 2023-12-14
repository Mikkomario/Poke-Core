package vf.poke.core.database.access.many.game.ability

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.game.AbilityFactory
import vf.poke.core.database.model.game.AbilityModel
import vf.poke.core.model.stored.game.Ability

object ManyAbilitiesAccess
{
	// NESTED	--------------------
	
	private class ManyAbilitiesSubView(condition: Condition) extends ManyAbilitiesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple abilities at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait ManyAbilitiesAccess 
	extends ManyRowModelAccess[Ability] with FilterableView[ManyAbilitiesAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * game ids of the accessible abilities
	  */
	def gameIds(implicit connection: Connection) = pullColumn(model.gameIdColumn).map { v => v.getInt }
	
	/**
	  * index in games of the accessible abilities
	  */
	def indicesInGames(implicit connection: Connection) = pullColumn(model.indexInGameColumn)
		.map { v => v.getInt }
	
	/**
	  * names of the accessible abilities
	  */
	def names(implicit connection: Connection) = pullColumn(model.nameColumn).flatMap { _.string }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = AbilityModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = AbilityFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyAbilitiesAccess = 
		new ManyAbilitiesAccess.ManyAbilitiesSubView(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	def inGame(gameId: Int) = filter(model.withGameId(gameId).toCondition)
	def withInGameIds(indices: Iterable[Int]) = filter(model.indexInGameColumn.in(indices))
	
	/**
	  * Updates the game ids of the targeted abilities
	  * @param newGameId A new game id to assign
	  * @return Whether any ability was affected
	  */
	def gameIds_=(newGameId: Int)(implicit connection: Connection) = putColumn(model.gameIdColumn, newGameId)
	
	/**
	  * Updates the index in games of the targeted abilities
	  * @param newIndexInGame A new index in game to assign
	  * @return Whether any ability was affected
	  */
	def indicesInGames_=(newIndexInGame: Int)(implicit connection: Connection) =
		putColumn(model.indexInGameColumn, newIndexInGame)
	
	/**
	  * Updates the names of the targeted abilities
	  * @param newName A new name to assign
	  * @return Whether any ability was affected
	  */
	def names_=(newName: String)(implicit connection: Connection) = putColumn(model.nameColumn, newName)
}

