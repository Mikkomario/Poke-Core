package vf.poke.core.database.model.randomization

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.core.database.factory.randomization.StarterAssignmentFactory
import vf.poke.core.model.partial.randomization.StarterAssignmentData
import vf.poke.core.model.stored.randomization.StarterAssignment

/**
  * 
	Used for constructing StarterAssignmentModel instances and for inserting starter assignments to the database
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object StarterAssignmentModel 
	extends DataInserter[StarterAssignmentModel, StarterAssignment, StarterAssignmentData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains starter assignment set id
	  */
	val setIdAttName = "setId"
	
	/**
	  * Name of the property that contains starter assignment poke id
	  */
	val pokeIdAttName = "pokeId"
	
	/**
	  * Name of the property that contains starter assignment placement
	  */
	val placementAttName = "placement"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains starter assignment set id
	  */
	def setIdColumn = table(setIdAttName)
	
	/**
	  * Column that contains starter assignment poke id
	  */
	def pokeIdColumn = table(pokeIdAttName)
	
	/**
	  * Column that contains starter assignment placement
	  */
	def placementColumn = table(placementAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = StarterAssignmentFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: StarterAssignmentData) = 
		apply(None, Some(data.setId), Some(data.pokeId), Some(data.placement))
	
	override protected def complete(id: Value, data: StarterAssignmentData) = StarterAssignment(id.getInt, 
		data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param id A starter assignment id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param placement A zero-based index that shows where this starter appears relative to the others.
	  *  The following index is typically strong against the previous index.
	  * @return A model containing only the specified placement
	  */
	def withPlacement(placement: Int) = apply(placement = Some(placement))
	
	/**
	  * @param pokeId Id of the poke that appears as a starter
	  * @return A model containing only the specified poke id
	  */
	def withPokeId(pokeId: Int) = apply(pokeId = Some(pokeId))
	
	/**
	  * @param setId Id of the starter set to which this starter belongs
	  * @return A model containing only the specified set id
	  */
	def withSetId(setId: Int) = apply(setId = Some(setId))
}

/**
  * Used for interacting with StarterAssignments in the database
  * @param id starter assignment database id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class StarterAssignmentModel(id: Option[Int] = None, setId: Option[Int] = None, 
	pokeId: Option[Int] = None, placement: Option[Int] = None) 
	extends StorableWithFactory[StarterAssignment]
{
	// IMPLEMENTED	--------------------
	
	override def factory = StarterAssignmentModel.factory
	
	override def valueProperties = {
		import StarterAssignmentModel._
		Vector("id" -> id, setIdAttName -> setId, pokeIdAttName -> pokeId, placementAttName -> placement)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param placement A zero-based index that shows where this starter appears relative to the others.
	  *  The following index is typically strong against the previous index.
	  * @return A new copy of this model with the specified placement
	  */
	def withPlacement(placement: Int) = copy(placement = Some(placement))
	
	/**
	  * @param pokeId Id of the poke that appears as a starter
	  * @return A new copy of this model with the specified poke id
	  */
	def withPokeId(pokeId: Int) = copy(pokeId = Some(pokeId))
	
	/**
	  * @param setId Id of the starter set to which this starter belongs
	  * @return A new copy of this model with the specified set id
	  */
	def withSetId(setId: Int) = copy(setId = Some(setId))
}

