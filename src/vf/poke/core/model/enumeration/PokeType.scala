package vf.poke.core.model.enumeration

import utopia.flow.collection.CollectionExtensions._
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.flow.generic.model.template.ValueConvertible
import utopia.flow.operator.equality.EqualsExtensions._
import utopia.flow.util.StringExtensions._
import vf.poke.core.model.cached.TypeSet

/**
  * Represents a damage type in the game
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
sealed trait PokeType extends ValueConvertible
{
	// ABSTRACT	--------------------
	
	/**
	  * id used to represent this poke type in database and json
	  */
	def id: Int
	
	/**
	 * @param defendingType Defending poke type
	 * @return Effectiveness modifier against that poke type
	 */
	def effectivenessAgainst(defendingType: PokeType): Double
	
	
	// IMPLEMENTED	--------------------
	
	override def toValue = id
	
	
	// OTHER    --------------------
	
	def effectivenessAgainst(defendingType: TypeSet): Double = defendingType.types.map(effectivenessAgainst).product
}

object PokeType
{
	// ATTRIBUTES	--------------------
	
	/**
	  * All available poke type values
	  */
	val values: Vector[PokeType] = 
		Vector(Normal, Fighting, Flying, Grass, Water, Fire, Rock, Ground, Psychic, Bug, Dragon, Electric, 
			Ghost, Poison, Ice, Steel, Dark, Fairy)
	
	
	// OTHER	--------------------
	
	/**
	  * @param id id representing a poke type
	  * @return poke type matching the specified id. None if the id didn't match any poke type
	  */
	def findForId(id: Int) = values.find { _.id == id }
	
	/**
	  * @param id id matching a poke type
	  * @return poke type matching that id. Failure if no matching value was found.
	  */
	def forId(id: Int) = 
		findForId(id).toTry { new NoSuchElementException(s"No value of PokeType matches id '$id'") }
	
	/**
	  * @param value A value representing an poke type id
	  * @return poke type matching the specified value, 
	  * when the value is interpreted as an poke type id. Failure if no matching value was found.
	  */
	def fromValue(value: Value) = forId(value.getInt)
	
	/**
	 * @param name Name of a type
	 * @return Type that best matches that name
	 */
	def findForName(name: String) =
		values.find { _.toString ~== name }.orElse {
			val options = values.filter { _.toString.isSimilarTo(name, 2) }
			if (options.hasSize > 1)
				options.find { _.toString.isSimilarTo(name, 1) }.orElse { options.maxByOption { _.toString.length } }
			else
				options.headOption
		}
	
	
	// NESTED	--------------------
	
	case object Bug extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 10
		
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Grass | Psychic | Dark => 2.0
			case Fire | Fighting | Poison | Flying | Ghost | Steel | Fairy => 0.5
			case _ => 1.0
		}
	}
	
	case object Dark extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 17
		
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Psychic | Ghost => 2.0
			case Fighting | Dark | Fairy => 0.5
			case _ => 1.0
		}
	}
	
	case object Dragon extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 11
		
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Dragon => 2.0
			case Steel => 0.5
			case Fairy => 0.0
			case _ => 1.0
		}
	}
	
	case object Electric extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 12
		
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Water | Flying => 2.0
			case Electric | Grass | Dragon => 0.5
			case Ground => 0.0
			case _ => 1.0
		}
	}
	
	case object Fairy extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 18
		
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Fighting | Dragon | Dark => 2.0
			case Fire | Poison | Steel => 0.5
			case _ => 1.0
		}
	}
	
	case object Fighting extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 2
		
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Normal | Ice | Rock | Dark | Steel  => 2.0
			case Poison | Flying | Psychic | Bug | Fairy => 0.5
			case Ghost => 0.0
			case _ => 1.0
		}
	}
	
	case object Fire extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 6
		
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Grass | Ice | Bug | Steel => 2.0
			case Fire | Water | Rock | Dragon => 0.5
			case _ => 1.0
		}
	}
	
	case object Flying extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 3
		
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Grass | Fighting | Bug => 2.0
			case Electric | Rock | Steel => 0.5
			case _ => 1.0
		}
	}
	
	case object Ghost extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 13
		
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Psychic | Ghost => 2.0
			case Dark => 0.5
			case Normal => 0.0
			case _ => 1.0
		}
	}
	
	case object Grass extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 4
		
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Water | Ground | Rock => 2.0
			case Fire | Grass | Poison | Flying | Bug | Dragon | Steel => 0.5
			case _ => 1.0
		}
	}
	
	case object Ground extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 8
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Fire | Electric | Poison | Rock | Steel => 2.0
			case Grass | Bug => 0.5
			case Flying => 0.0
			case _ => 1.0
		}
		
	}
	
	case object Ice extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 15
		
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Grass | Ground | Flying | Dragon => 2.0
			case Fire | Water | Ice | Steel => 0.5
			case _ => 1.0
		}
	}
	
	case object Normal extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 1
		
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Rock | Steel => 0.5
			case Ghost => 0.0
			case _ => 1.0
		}
	}
	
	case object Poison extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 14
		
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Grass | Fairy => 2.0
			case Poison | Ground | Rock | Ghost => 0.5
			case Steel => 0.0
			case _ => 1.0
		}
	}
	
	case object Psychic extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 9
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Fighting | Poison => 2.0
			case Psychic | Steel => 0.5
			case Dark => 0.0
			case _ => 1.0
		}
		
	}
	
	case object Rock extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 7
		
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Fire | Ice | Flying | Bug => 2.0
			case Fighting | Ground |Steel => 0.5
			case _ => 1.0
		}
	}
	
	case object Steel extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 16
		
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Ice | Fairy | Rock => 2.0
			case Fire | Water | Electric | Steel => 0.5
			case _ => 1.0
		}
	}
	
	case object Water extends PokeType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 5
		
		
		// IMPLEMENTED  --------------------
		
		override def effectivenessAgainst(defendingType: PokeType): Double = defendingType match {
			case Fire | Ground | Rock => 2.0
			case Water | Grass | Dragon => 0.5
			case _ => 1.0
		}
	}
}

