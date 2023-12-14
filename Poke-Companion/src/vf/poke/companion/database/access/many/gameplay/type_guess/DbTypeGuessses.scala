package vf.poke.companion.database.access.many.gameplay.type_guess

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView}
import vf.poke.companion.model.stored.gameplay.TypeGuess

/**
  * The root access point when targeting multiple type guessses at a time
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object DbTypeGuessses extends ManyTypeGuesssesAccess with NonDeprecatedView[TypeGuess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) type guessses
	  */
	def includingHistory = DbTypeGuesssesIncludingHistory
	
	
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted type guessses
	  * @return An access point to type guessses with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbTypeGuesssesSubset(ids)
	
	
	// NESTED	--------------------
	
	object DbTypeGuesssesIncludingHistory extends ManyTypeGuesssesAccess with UnconditionalView
	
	class DbTypeGuesssesSubset(targetIds: Set[Int]) extends ManyTypeGuesssesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

