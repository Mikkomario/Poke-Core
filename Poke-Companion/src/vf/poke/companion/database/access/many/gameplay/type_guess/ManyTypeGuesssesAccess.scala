package vf.poke.companion.database.access.many.gameplay.type_guess

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NullDeprecatableView
import utopia.vault.sql.Condition
import vf.poke.companion.database.factory.gameplay.TypeGuessFactory
import vf.poke.companion.database.model.gameplay.TypeGuessModel
import vf.poke.companion.model.stored.gameplay.TypeGuess
import vf.poke.core.model.enumeration.PokeType

import java.time.Instant

object ManyTypeGuesssesAccess
{
	// NESTED	--------------------
	
	private class ManyTypeGuesssesSubView(condition: Condition) extends ManyTypeGuesssesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple type guessses at a time
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
trait ManyTypeGuesssesAccess 
	extends ManyRowModelAccess[TypeGuess] with NullDeprecatableView[ManyTypeGuesssesAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * run ids of the accessible type guessses
	  */
	def runIds(implicit connection: Connection) = pullColumn(model.runIdColumn).map { v => v.getInt }
	
	/**
	  * poke ids of the accessible type guessses
	  */
	def pokeIds(implicit connection: Connection) = pullColumn(model.pokeIdColumn).map { v => v.getInt }
	
	/**
	  * guessed types of the accessible type guessses
	  */
	def guessedTypes(implicit connection: Connection) = 
		pullColumn(model.guessedTypeColumn).map { v => v.getInt }.flatMap(PokeType.findForId)
	
	/**
	  * creation times of the accessible type guessses
	  */
	def creationTimes(implicit connection: Connection) = pullColumn(model.createdColumn)
		.map { v => v.getInstant }
	
	/**
	  * deprecation times of the accessible type guessses
	  */
	def deprecationTimes(implicit connection: Connection) = 
		pullColumn(model.deprecatedAfterColumn).flatMap { v => v.instant }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = TypeGuessModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = TypeGuessFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyTypeGuesssesAccess = 
		new ManyTypeGuesssesAccess.ManyTypeGuesssesSubView(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the creation times of the targeted type guessses
	  * @param newCreated A new created to assign
	  * @return Whether any type guess was affected
	  */
	def creationTimes_=(newCreated: Instant)(implicit connection: Connection) = 
		putColumn(model.createdColumn, newCreated)
	
	/**
	  * Updates the deprecation times of the targeted type guessses
	  * @param newDeprecatedAfter A new deprecated after to assign
	  * @return Whether any type guess was affected
	  */
	def deprecationTimes_=(newDeprecatedAfter: Instant)(implicit connection: Connection) = 
		putColumn(model.deprecatedAfterColumn, newDeprecatedAfter)
	
	/**
	  * Updates the guessed types of the targeted type guessses
	  * @param newGuessedType A new guessed type to assign
	  * @return Whether any type guess was affected
	  */
	def guessedTypes_=(newGuessedType: PokeType)(implicit connection: Connection) = 
		putColumn(model.guessedTypeColumn, newGuessedType.id)
	
	/**
	  * Updates the poke ids of the targeted type guessses
	  * @param newPokeId A new poke id to assign
	  * @return Whether any type guess was affected
	  */
	def pokeIds_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
	
	/**
	  * Updates the run ids of the targeted type guessses
	  * @param newRunId A new run id to assign
	  * @return Whether any type guess was affected
	  */
	def runIds_=(newRunId: Int)(implicit connection: Connection) = putColumn(model.runIdColumn, newRunId)
}

