package vf.poke.core.model.combined.randomization

import utopia.flow.view.template.Extender
import vf.poke.core.model.partial.randomization.MoveData
import vf.poke.core.model.stored.poke.MoveLearn
import vf.poke.core.model.stored.randomization.Move

/**
  * Represents a move learned by a poke. Includes full move details.
  * @author Mikko Hilpinen
  * @since 13.12.2023, v1.0
  */
case class LearntMove(move: Move, learn: MoveLearn) extends Extender[MoveData]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of this move in the database
	  */
	def id = move.id
	
	
	// IMPLEMENTED	--------------------
	
	override def wrapped = move.data
}

