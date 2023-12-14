package vf.poke.core.database.model.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.core.database.factory.poke.EvoMoveFactory
import vf.poke.core.model.partial.poke.EvoMoveData
import vf.poke.core.model.stored.poke.EvoMove

/**
  * Used for constructing EvoMoveModel instances and for inserting evo moves to the database
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object EvoMoveModel extends DataInserter[EvoMoveModel, EvoMove, EvoMoveData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains evo move poke id
	  */
	val pokeIdAttName = "pokeId"
	
	/**
	  * Name of the property that contains evo move move id
	  */
	val moveIdAttName = "moveId"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains evo move poke id
	  */
	def pokeIdColumn = table(pokeIdAttName)
	
	/**
	  * Column that contains evo move move id
	  */
	def moveIdColumn = table(moveIdAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = EvoMoveFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: EvoMoveData) = apply(None, Some(data.pokeId), Some(data.moveId))
	
	override protected def complete(id: Value, data: EvoMoveData) = EvoMove(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param id A evo move id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param moveId id of the move learnt
	  * @return A model containing only the specified move id
	  */
	def withMoveId(moveId: Int) = apply(moveId = Some(moveId))
	
	/**
	  * @param pokeId Id of the poke (form) learning this move
	  * @return A model containing only the specified poke id
	  */
	def withPokeId(pokeId: Int) = apply(pokeId = Some(pokeId))
}

/**
  * Used for interacting with EvoMoves in the database
  * @param id evo move database id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class EvoMoveModel(id: Option[Int] = None, pokeId: Option[Int] = None, moveId: Option[Int] = None) 
	extends StorableWithFactory[EvoMove]
{
	// IMPLEMENTED	--------------------
	
	override def factory = EvoMoveModel.factory
	
	override def valueProperties = {
		import EvoMoveModel._
		Vector("id" -> id, pokeIdAttName -> pokeId, moveIdAttName -> moveId)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param moveId id of the move learnt
	  * @return A new copy of this model with the specified move id
	  */
	def withMoveId(moveId: Int) = copy(moveId = Some(moveId))
	
	/**
	  * @param pokeId Id of the poke (form) learning this move
	  * @return A new copy of this model with the specified poke id
	  */
	def withPokeId(pokeId: Int) = copy(pokeId = Some(pokeId))
}

