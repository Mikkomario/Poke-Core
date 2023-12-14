package vf.poke.core.database.factory.randomization

import utopia.vault.nosql.factory.row.linked.CombiningFactory
import vf.poke.core.database.factory.poke.MoveLearnFactory
import vf.poke.core.model.combined.randomization.LearntMove
import vf.poke.core.model.stored.poke.MoveLearn
import vf.poke.core.model.stored.randomization.Move

/**
  * Used for reading learnt moves from the database
  * @author Mikko Hilpinen
  * @since 13.12.2023, v1.0
  */
object LearntMoveFactory extends CombiningFactory[LearntMove, Move, MoveLearn]
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = MoveLearnFactory
	
	override def parentFactory = MoveFactory
	
	override def apply(move: Move, learn: MoveLearn) = LearntMove(move, learn)
}

