package vf.poke.core.database.access.single.game

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.{Condition, OrderBy}
import vf.poke.core.database.factory.game.GameFactory
import vf.poke.core.database.model.game.GameModel
import vf.poke.core.model.stored.game.Game

/**
  * Used for accessing individual games
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbGame extends SingleRowModelAccess[Game] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = GameModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = GameFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted game
	  * @return An access point to that game
	  */
	def apply(id: Int) = DbSingleGame(id)
	
	/**
	 * @param name Name of the targeted game
	 * @return Access to that game
	 */
	def withName(name: String) = filterDistinct(model.withName(name).toCondition)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique games.
	  * @return An access point to the game that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniqueGameAccess(mergeCondition(condition))
}

