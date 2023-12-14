package vf.poke.core.database.access.many.game

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.nosql.view.UnconditionalView

/**
  * The root access point when targeting multiple games at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbGames extends ManyGamesAccess with UnconditionalView
{
	// OTHER	--------------------
	
	/**
	  * @param ids Ids of the targeted games
	  * @return An access point to games with the specified ids
	  */
	def apply(ids: Set[Int]) = new DbGamesSubset(ids)
	
	
	// NESTED	--------------------
	
	class DbGamesSubset(targetIds: Set[Int]) extends ManyGamesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(index in targetIds)
	}
}

