package vf.poke.core.database.access.many.game

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.game.GameFactory
import vf.poke.core.database.model.game.GameModel
import vf.poke.core.model.stored.game.Game

object ManyGamesAccess
{
	// NESTED	--------------------
	
	private class ManyGamesSubView(condition: Condition) extends ManyGamesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple games at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait ManyGamesAccess extends ManyRowModelAccess[Game] with FilterableView[ManyGamesAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * names of the accessible games
	  */
	def names(implicit connection: Connection) = pullColumn(model.nameColumn).flatMap { _.string }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = GameModel
	
	/**
	 * @param connection Implicit DB Connection
	 * @return Accessible games as a map where keys are game ids and values are game names
	 */
	def toMap(implicit connection: Connection) =
		pullColumnMap(index, model.nameColumn).map { case (idVal, nameVal) => idVal.getInt -> nameVal.getString }
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = GameFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyGamesAccess = 
		new ManyGamesAccess.ManyGamesSubView(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	 * @param namePart Part of a game's name
	 * @return Access to games with a name that contains the sepcified string
	 */
	def withNamesContaining(namePart: String) = filter(model.nameColumn.contains(namePart))
	
	/**
	  * Updates the names of the targeted games
	  * @param newName A new name to assign
	  * @return Whether any game was affected
	  */
	def names_=(newName: String)(implicit connection: Connection) = putColumn(model.nameColumn, newName)
}

