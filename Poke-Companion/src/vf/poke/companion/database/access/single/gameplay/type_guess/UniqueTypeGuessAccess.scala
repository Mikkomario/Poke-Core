package vf.poke.companion.database.access.single.gameplay.type_guess

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import utopia.vault.sql.Condition
import vf.poke.companion.database.factory.gameplay.TypeGuessFactory
import vf.poke.companion.database.model.gameplay.TypeGuessModel
import vf.poke.companion.model.stored.gameplay.TypeGuess
import vf.poke.core.model.enumeration.PokeType

import java.time.Instant

object UniqueTypeGuessAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniqueTypeGuessAccess = new _UniqueTypeGuessAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniqueTypeGuessAccess(condition: Condition) extends UniqueTypeGuessAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct type guessses.
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
trait UniqueTypeGuessAccess 
	extends SingleRowModelAccess[TypeGuess] with NullDeprecatableView[UniqueTypeGuessAccess] 
		with DistinctModelAccess[TypeGuess, Option[TypeGuess], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the game run on which this guess was made. None if no type guess (or value) was found.
	  */
	def runId(implicit connection: Connection) = pullColumn(model.runIdColumn).int
	
	/**
	  * Id of the described poke. None if no type guess (or value) was found.
	  */
	def pokeId(implicit connection: Connection) = pullColumn(model.pokeIdColumn).int
	
	/**
	  * Poke type of this type guess. None if no type guess (or value) was found.
	  */
	def guessedType(implicit connection: Connection) = 
		pullColumn(model.guessedTypeColumn).int.flatMap(PokeType.findForId)
	
	/**
	  * Time when this type guess was added to the database. None if no type guess (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.createdColumn).instant
	
	/**
	  * 
		Time when this guess was replaced or when it became invalid. None if no type guess (or value) was found.
	  */
	def deprecatedAfter(implicit connection: Connection) = pullColumn(model.deprecatedAfterColumn).instant
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = TypeGuessModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = TypeGuessFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniqueTypeGuessAccess = 
		new UniqueTypeGuessAccess._UniqueTypeGuessAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the creation times of the targeted type guessses
	  * @param newCreated A new created to assign
	  * @return Whether any type guess was affected
	  */
	def created_=(newCreated: Instant)(implicit connection: Connection) = 
		putColumn(model.createdColumn, newCreated)
	
	/**
	  * Updates the deprecation times of the targeted type guessses
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any type guess was affected
	  */
	def deprecatedAfter_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfterColumn, newDeprecatedAfter)
	
	/**
	  * Updates the guessed types of the targeted type guessses
	  * @param newGuessedType A new guessed type to assign
	  * @return Whether any type guess was affected
	  */
	def guessedType_=(newGuessedType: PokeType)(implicit connection: Connection) = 
		putColumn(model.guessedTypeColumn, newGuessedType.id)
	
	/**
	  * Updates the poke ids of the targeted type guessses
	  * @param newPokeId A new poke id to assign
	  * @return Whether any type guess was affected
	  */
	def pokeId_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
	
	/**
	  * Updates the run ids of the targeted type guessses
	  * @param newRunId A new run id to assign
	  * @return Whether any type guess was affected
	  */
	def runId_=(newRunId: Int)(implicit connection: Connection) = putColumn(model.runIdColumn, newRunId)
}

