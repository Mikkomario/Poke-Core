package vf.poke.core.model.combined.poke

import utopia.flow.view.template.Extender
import vf.poke.core.model.partial.poke.PokeData
import vf.poke.core.model.stored.poke.Poke
import vf.poke.core.model.stored.randomization.StarterAssignment

/**
  * Represents a starter poke
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class Starter(poke: Poke, starterAssignment: StarterAssignment) extends Extender[PokeData]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of this poke in the database
	  */
	def id = poke.id
	
	
	// IMPLEMENTED	--------------------
	
	override def wrapped = poke.data
}

