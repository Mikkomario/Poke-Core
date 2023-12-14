package vf.poke.core.model.enumeration

import utopia.flow.collection.CollectionExtensions._
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.flow.generic.model.template.ValueConvertible

/**
  * Represents a poke's attribute in the game
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
sealed trait Stat extends ValueConvertible
{
	// ABSTRACT	--------------------
	
	/**
	  * id used to represent this stat in database and json
	  */
	def id: Int
	
	def minimumValue: Int
	
	
	// IMPLEMENTED	--------------------
	
	override def toValue = id
	
	
	// OTHER    ------------------------
	
	def otherRandom = Stat.values.filterNot { _ == this }.random
}

object Stat
{
	// ATTRIBUTES	--------------------
	
	val maxValue = 255
	
	/**
	  * All available stat values
	  */
	val values: Vector[Stat] = Vector(Hp, Attack, SpecialAttack, Defense, SpecialDefense, Speed)
	
	
	// OTHER	--------------------
	
	/**
	  * @param id id representing a stat
	  * @return stat matching the specified id. None if the id didn't match any stat
	  */
	def findForId(id: Int) = values.find { _.id == id }
	
	/**
	  * @param id id matching a stat
	  * @return stat matching that id. Failure if no matching value was found.
	  */
	def forId(id: Int) = findForId(id).toTry { new NoSuchElementException(
		s"No value of Stat matches id '$id'") }
	
	/**
	  * @param value A value representing an stat id
	  * @return stat matching the specified value, 
	  * when the value is interpreted as an stat id. Failure if no matching value was found.
	  */
	def fromValue(value: Value) = forId(value.getInt)
	
	
	// NESTED	--------------------
	
	case object Attack extends Stat
	{
		// ATTRIBUTES	--------------------
		
		override val id = 2
		override def minimumValue: Int = 10
	}
	
	case object Defense extends Stat
	{
		// ATTRIBUTES	--------------------
		
		override val id = 4
		override def minimumValue: Int = 10
	}
	
	case object Hp extends Stat
	{
		// ATTRIBUTES	--------------------
		
		override val id = 1
		override def minimumValue: Int = 20
	}
	
	case object SpecialAttack extends Stat
	{
		// ATTRIBUTES	--------------------
		
		override val id = 3
		override def minimumValue: Int = 10
	}
	
	case object SpecialDefense extends Stat
	{
		// ATTRIBUTES	--------------------
		
		override val id = 5
		override def minimumValue: Int = 10
	}
	
	case object Speed extends Stat
	{
		// ATTRIBUTES	--------------------
		
		override val id = 6
		override def minimumValue: Int = 10
	}
}

