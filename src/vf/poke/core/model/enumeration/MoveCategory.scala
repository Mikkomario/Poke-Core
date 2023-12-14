package vf.poke.core.model.enumeration

import utopia.flow.collection.CollectionExtensions._
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.flow.generic.model.template.ValueConvertible

import java.util.NoSuchElementException

/**
  * An enumeration for whether something is physical-focused or special-focused
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
sealed trait MoveCategory extends ValueConvertible
{
	// ABSTRACT	--------------------
	
	/**
	  * id used to represent this move category in database and json
	  */
	def id: Int
	
	
	// IMPLEMENTED	--------------------
	
	override def toValue = id
}

object MoveCategory
{
	// ATTRIBUTES	--------------------
	
	/**
	  * All available move category values
	  */
	val values: Vector[MoveCategory] = Vector(Physical, Special)
	
	
	// OTHER	--------------------
	
	/**
	  * @param id id representing a move category
	  * @return move category matching the specified id. None if the id didn't match any move category
	  */
	def findForId(id: Int) = values.find { _.id == id }
	
	/**
	  * @param id id matching a move category
	  * @return move category matching that id. Failure if no matching value was found.
	  */
	def forId(id: Int) = 
		findForId(id).toTry { new NoSuchElementException(s"No value of MoveCategory matches id '$id'") }
	
	/**
	  * @param value A value representing an move category id
	  * @return move category matching the specified value, 
	  * when the value is interpreted as an move category id. Failure if no matching value was found.
	  */
	def fromValue(value: Value) = forId(value.getInt)
	
	
	// NESTED	--------------------
	
	case object Physical extends MoveCategory
	{
		// ATTRIBUTES	--------------------
		
		override val id = 1
	}
	
	case object Special extends MoveCategory
	{
		// ATTRIBUTES	--------------------
		
		override val id = 2
	}
}

