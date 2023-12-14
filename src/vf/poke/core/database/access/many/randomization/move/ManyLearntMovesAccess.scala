package vf.poke.core.database.access.many.randomization.move

import utopia.flow.collection.immutable.range.HasInclusiveEnds
import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.randomization.LearntMoveFactory
import vf.poke.core.database.model.poke.MoveLearnModel
import vf.poke.core.model.combined.randomization.LearntMove

object ManyLearntMovesAccess
{
	// NESTED	--------------------
	
	private class SubAccess(condition: Condition) extends ManyLearntMovesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return multiple learnt moves at a time
  * @author Mikko Hilpinen
  * @since 13.12.2023
  */
trait ManyLearntMovesAccess 
	extends ManyMovesAccessLike[LearntMove, ManyLearntMovesAccess] with ManyRowModelAccess[LearntMove]
{
	// COMPUTED	--------------------
	
	/**
	  * poke ids of the accessible move learns
	  */
	def learnPokeIds(implicit connection: Connection) = pullColumn(learnModel.pokeIdColumn)
		.map { v => v.getInt }
	
	/**
	  * move ids of the accessible move learns
	  */
	def learnMoveIds(implicit connection: Connection) = pullColumn(learnModel.moveIdColumn)
		.map { v => v.getInt }
	
	/**
	  * levels of the accessible move learns
	  */
	def learnLevels(implicit connection: Connection) = pullColumn(learnModel.levelColumn)
		.map { v => v.getInt }
	
	/**
	  * Model (factory) used for interacting the move learns associated with this learnt move
	  */
	protected def learnModel = MoveLearnModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = LearntMoveFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyLearntMovesAccess = 
		new ManyLearntMovesAccess.SubAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	 * @param pokeIds Ids of the targeted pokes
	 * @return Access to moves learnt by those pokes
	 */
	def byPokes(pokeIds: Iterable[Int]) = filter(learnModel.pokeIdColumn.in(pokeIds))
	/**
	 * @param levelRange Targeted level range
	 * @return Access to moves learnt during those levels
	 */
	def duringLevels(levelRange: HasInclusiveEnds[Int]) =
		filter(learnModel.levelColumn.isBetween(levelRange.ends.map { lvl => lvl }))
	
	/**
	  * Updates the levels of the targeted move learns
	  * @param newLevel A new level to assign
	  * @return Whether any move learn was affected
	  */
	def learnLevels_=(newLevel: Int)(implicit connection: Connection) = 
		putColumn(learnModel.levelColumn, newLevel)
	
	/**
	  * Updates the move ids of the targeted move learns
	  * @param newMoveId A new move id to assign
	  * @return Whether any move learn was affected
	  */
	def learnMoveIds_=(newMoveId: Int)(implicit connection: Connection) = 
		putColumn(learnModel.moveIdColumn, newMoveId)
	
	/**
	  * Updates the poke ids of the targeted move learns
	  * @param newPokeId A new poke id to assign
	  * @return Whether any move learn was affected
	  */
	def learnPokeIds_=(newPokeId: Int)(implicit connection: Connection) = 
		putColumn(learnModel.pokeIdColumn, newPokeId)
}

