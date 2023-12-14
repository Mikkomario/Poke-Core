package vf.poke.core.database.model.randomization

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.core.database.factory.randomization.StarterSetFactory
import vf.poke.core.model.partial.randomization.StarterSetData
import vf.poke.core.model.stored.randomization.StarterSet

/**
  * Used for constructing StarterSetModel instances and for inserting starter sets to the database
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object StarterSetModel extends DataInserter[StarterSetModel, StarterSet, StarterSetData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains starter set randomization id
	  */
	val randomizationIdAttName = "randomizationId"
	
	/**
	  * Name of the property that contains starter set placement
	  */
	val placementAttName = "placement"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains starter set randomization id
	  */
	def randomizationIdColumn = table(randomizationIdAttName)
	
	/**
	  * Column that contains starter set placement
	  */
	def placementColumn = table(placementAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = StarterSetFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: StarterSetData) = apply(None, Some(data.randomizationId), Some(data.placement))
	
	override protected def complete(id: Value, data: StarterSetData) = StarterSet(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param id A starter set id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param placement Relative set index within the game, 0-based
	  * @return A model containing only the specified placement
	  */
	def withPlacement(placement: Int) = apply(placement = Some(placement))
	
	/**
	  * @param randomizationId Id of the randomization where these starters appear
	  * @return A model containing only the specified randomization id
	  */
	def withRandomizationId(randomizationId: Int) = apply(randomizationId = Some(randomizationId))
}

/**
  * Used for interacting with StarterSets in the database
  * @param id starter set database id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class StarterSetModel(id: Option[Int] = None, randomizationId: Option[Int] = None, 
	placement: Option[Int] = None) 
	extends StorableWithFactory[StarterSet]
{
	// IMPLEMENTED	--------------------
	
	override def factory = StarterSetModel.factory
	
	override def valueProperties = {
		import StarterSetModel._
		Vector("id" -> id, randomizationIdAttName -> randomizationId, placementAttName -> placement)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param placement Relative set index within the game, 0-based
	  * @return A new copy of this model with the specified placement
	  */
	def withPlacement(placement: Int) = copy(placement = Some(placement))
	
	/**
	  * @param randomizationId Id of the randomization where these starters appear
	  * @return A new copy of this model with the specified randomization id
	  */
	def withRandomizationId(randomizationId: Int) = copy(randomizationId = Some(randomizationId))
}

