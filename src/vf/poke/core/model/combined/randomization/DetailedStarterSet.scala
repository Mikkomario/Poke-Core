package vf.poke.core.model.combined.randomization

import utopia.flow.view.template.Extender
import vf.poke.core.model.partial.randomization.StarterSetData
import vf.poke.core.model.stored.randomization.{StarterAssignment, StarterSet}

/**
  * Includes the specific starter assignments in a starter set
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DetailedStarterSet(set: StarterSet, assignments: Vector[StarterAssignment]) 
	extends Extender[StarterSetData]
{
	// COMPUTED	--------------------
	
	/**
	  * Id of this set in the database
	  */
	def id = set.id
	
	/**
	 * @return Ids of the pokes used as starters
	 */
	def pokeIds = assignments.map { _.pokeId }
	
	
	// IMPLEMENTED	--------------------
	
	override def wrapped = set.data
}

