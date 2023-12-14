package vf.poke.core.database.model.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.core.database.factory.poke.MoveLearnFactory
import vf.poke.core.model.partial.poke.MoveLearnData
import vf.poke.core.model.stored.poke.MoveLearn

/**
  * Used for constructing MoveLearnModel instances and for inserting move learns to the database
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object MoveLearnModel extends DataInserter[MoveLearnModel, MoveLearn, MoveLearnData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains move learn poke id
	  */
	val pokeIdAttName = "pokeId"
	
	/**
	  * Name of the property that contains move learn move id
	  */
	val moveIdAttName = "moveId"
	
	/**
	  * Name of the property that contains move learn level
	  */
	val levelAttName = "level"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains move learn poke id
	  */
	def pokeIdColumn = table(pokeIdAttName)
	
	/**
	  * Column that contains move learn move id
	  */
	def moveIdColumn = table(moveIdAttName)
	
	/**
	  * Column that contains move learn level
	  */
	def levelColumn = table(levelAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = MoveLearnFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: MoveLearnData) = apply(None, Some(data.pokeId), Some(data.moveId), 
		Some(data.level))
	
	override protected def complete(id: Value, data: MoveLearnData) = MoveLearn(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param id A move learn id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param level Level at which this move is learnt, if applicable.
	  * @return A model containing only the specified level
	  */
	def withLevel(level: Int) = apply(level = Some(level))
	
	/**
	  * @param moveId Id of the move learnt
	  * @return A model containing only the specified move id
	  */
	def withMoveId(moveId: Int) = apply(moveId = Some(moveId))
	
	/**
	  * @param pokeId Id of the poke that learns the move
	  * @return A model containing only the specified poke id
	  */
	def withPokeId(pokeId: Int) = apply(pokeId = Some(pokeId))
}

/**
  * Used for interacting with MoveLearns in the database
  * @param id move learn database id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class MoveLearnModel(id: Option[Int] = None, pokeId: Option[Int] = None, moveId: Option[Int] = None, 
	level: Option[Int] = None) 
	extends StorableWithFactory[MoveLearn]
{
	// IMPLEMENTED	--------------------
	
	override def factory = MoveLearnModel.factory
	
	override def valueProperties = {
		import MoveLearnModel._
		Vector("id" -> id, pokeIdAttName -> pokeId, moveIdAttName -> moveId, levelAttName -> level)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param level Level at which this move is learnt, if applicable.
	  * @return A new copy of this model with the specified level
	  */
	def withLevel(level: Int) = copy(level = Some(level))
	
	/**
	  * @param moveId Id of the move learnt
	  * @return A new copy of this model with the specified move id
	  */
	def withMoveId(moveId: Int) = copy(moveId = Some(moveId))
	
	/**
	  * @param pokeId Id of the poke that learns the move
	  * @return A new copy of this model with the specified poke id
	  */
	def withPokeId(pokeId: Int) = copy(pokeId = Some(pokeId))
}

