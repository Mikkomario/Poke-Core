package vf.poke.core.database.access.single.randomization

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleChronoRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.RandomizationFactory
import vf.poke.core.database.model.randomization.RandomizationModel
import vf.poke.core.model.stored.randomization.Randomization

import java.time.Instant

object UniqueRandomizationAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueRandomizationAccess = new _UniqueRandomizationAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueRandomizationAccess(condition: Condition) extends UniqueRandomizationAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct randomizations.
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniqueRandomizationAccess 
	extends SingleChronoRowModelAccess[Randomization, UniqueRandomizationAccess] 
		with DistinctModelAccess[Randomization, Option[Randomization], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the targeted game / ROM. None if no randomization (or value) was found.
	  */
	def gameId(implicit connection: Connection) = pullColumn(model.gameIdColumn).int
	
	/**
	  * Time when this randomization was added to the database. None if no randomization (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.createdColumn).instant
	
	/**
	  * True if this represents an unmodified copy of the game. None if no randomization (or value) was found.
	  */
	def isOriginal(implicit connection: Connection) = pullColumn(model.isOriginalColumn).boolean
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = RandomizationModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = RandomizationFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueRandomizationAccess = 
		new UniqueRandomizationAccess._UniqueRandomizationAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the creation times of the targeted randomizations
	  * @param newCreated A new created to assign
	  * @return Whether any randomization was affected
	  */
	def created_=(newCreated: Instant)(implicit connection: Connection) = 
		putColumn(model.createdColumn, newCreated)
	
	/**
	  * Updates the game ids of the targeted randomizations
	  * @param newGameId A new game id to assign
	  * @return Whether any randomization was affected
	  */
	def gameId_=(newGameId: Int)(implicit connection: Connection) = putColumn(model.gameIdColumn, newGameId)
	
	/**
	  * Updates the are original of the targeted randomizations
	  * @param newIsOriginal A new is original to assign
	  * @return Whether any randomization was affected
	  */
	def isOriginal_=(newIsOriginal: Boolean)(implicit connection: Connection) = 
		putColumn(model.isOriginalColumn, newIsOriginal)
}

