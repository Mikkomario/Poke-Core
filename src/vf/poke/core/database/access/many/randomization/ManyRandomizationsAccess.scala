package vf.poke.core.database.access.many.randomization

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.ChronoRowFactoryView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.RandomizationFactory
import vf.poke.core.database.model.game.GameModel
import vf.poke.core.database.model.randomization.RandomizationModel
import vf.poke.core.model.stored.randomization.Randomization

import java.time.Instant

object ManyRandomizationsAccess
{
	// NESTED	--------------------
	
	private class ManyRandomizationsSubView(condition: Condition) extends ManyRandomizationsAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple randomizations at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait ManyRandomizationsAccess 
	extends ManyRowModelAccess[Randomization] 
		with ChronoRowFactoryView[Randomization, ManyRandomizationsAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * game ids of the accessible randomizations
	  */
	def gameIds(implicit connection: Connection) = pullColumn(model.gameIdColumn).map { v => v.getInt }
	
	/**
	  * creation times of the accessible randomizations
	  */
	def creationTimes(implicit connection: Connection) = pullColumn(model.createdColumn)
		.map { v => v.getInstant }
	
	/**
	  * are original of the accessible randomizations
	  */
	def areOriginal(implicit connection: Connection) = 
		pullColumn(model.isOriginalColumn).map { v => v.getBoolean }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = RandomizationModel
	
	protected def gameModel = GameModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = RandomizationFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyRandomizationsAccess = 
		new ManyRandomizationsAccess.ManyRandomizationsSubView(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	 * @param gameIds Ids of the targeted games
	 * @return Access to randomizations for those games
	 */
	def forGames(gameIds: Iterable[Int]) = filter(model.gameIdColumn.in(gameIds))
	
	/**
	 * @param gameNamePart A string that may appear in a game name
	 * @param connection Implicit DB connection
	 * @return Accessible randomizations for games containing that string
	 */
	def findForGamesContaining(gameNamePart: String)(implicit connection: Connection) =
		find(gameModel.nameColumn.contains(gameNamePart), joins = Vector(gameModel.table))
	
	/**
	  * Updates the are original of the targeted randomizations
	  * @param newIsOriginal A new is original to assign
	  * @return Whether any randomization was affected
	  */
	def areOriginal_=(newIsOriginal: Boolean)(implicit connection: Connection) = 
		putColumn(model.isOriginalColumn, newIsOriginal)
	
	/**
	  * Updates the creation times of the targeted randomizations
	  * @param newCreated A new created to assign
	  * @return Whether any randomization was affected
	  */
	def creationTimes_=(newCreated: Instant)(implicit connection: Connection) = 
		putColumn(model.createdColumn, newCreated)
	
	/**
	  * Updates the game ids of the targeted randomizations
	  * @param newGameId A new game id to assign
	  * @return Whether any randomization was affected
	  */
	def gameIds_=(newGameId: Int)(implicit connection: Connection) = putColumn(model.gameIdColumn, newGameId)
}

