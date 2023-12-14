package vf.poke.core.model.enumeration

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.flow.generic.model.template.ValueConvertible

/**
  * Common trait for all critical rate values
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
sealed trait CriticalRate extends ValueConvertible
{
	// ABSTRACT	--------------------
	
	/**
	  * id used to represent this critical rate in database and json
	  */
	def id: Int
	
	
	// IMPLEMENTED	--------------------
	
	override def toValue = id
}

object CriticalRate
{
	// ATTRIBUTES	--------------------
	
	/**
	  * All available critical rate values
	  */
	val values: Vector[CriticalRate] = Vector(Normal, Increased, Guaranteed)
	
	
	// COMPUTED	--------------------
	
	/**
	  * The default critical rate (i.e. normal)
	  */
	def default = Normal
	
	
	// OTHER	--------------------
	
	/**
	  * @param id id representing a critical rate
	  * @return critical rate matching the specified id. None if the id didn't match any critical rate
	  */
	def findForId(id: Int) = values.find { _.id == id }
	
	/**
	  * @param id id matching a critical rate
	  * @return critical rate matching that id, or the default critical rate (normal)
	  */
	def forId(id: Int) = findForId(id).getOrElse(default)
	
	/**
	  * @param value A value representing an critical rate id
	  * @return critical rate matching the specified value, 
		when the value is interpreted as an critical rate id, 
	  * or the default critical rate (normal)
	  */
	def fromValue(value: Value) = forId(value.getInt)
	
	
	// NESTED	--------------------
	
	case object Guaranteed extends CriticalRate
	{
		// ATTRIBUTES	--------------------
		
		override val id = 3
	}
	
	case object Increased extends CriticalRate
	{
		// ATTRIBUTES	--------------------
		
		override val id = 2
	}
	
	case object Normal extends CriticalRate
	{
		// ATTRIBUTES	--------------------
		
		override val id = 1
	}
}

