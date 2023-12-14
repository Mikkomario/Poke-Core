package vf.poke.core.model.cached

import utopia.flow.collection.immutable.Pair
import utopia.flow.collection.CollectionExtensions._
import vf.poke.core.model.enumeration.PokeType

object TypeSet
{
	def apply(primary: PokeType, secondary: PokeType): TypeSet =
		new TypeSet(primary, Some(secondary).filterNot { _ == primary })
}

/**
 * Represents a combination of 1-2 types
 * @author Mikko Hilpinen
 * @since 3.7.2023, v1.0-alt
 */
case class TypeSet(primary: PokeType, secondary: Option[PokeType] = None)
{
	// COMPUTED ----------------------
	
	def types: IndexedSeq[PokeType] = secondary match {
		case Some(s) => Pair(primary, s)
		case None => Vector(primary)
	}
	
	def isSingleType = secondary.isEmpty
	def isDualType = !isSingleType
	
	def random = types.random
	
	def effectiveness = EffectivenessRelations(this)
	
	
	// IMPLEMENTED  -----------------
	
	override def toString = secondary match {
		case Some(secondary) => s"$primary|$secondary"
		case None => primary.toString
	}
	
	
	// OTHER    ---------------------
	
	def contains(t: PokeType) = primary == t || secondary.contains(t)
	
	def &&(other: TypeSet) = {
		if (contains(other.primary)) {
			if (other.secondary.exists(contains))
				Some(this)
			else
				Some(TypeSet(other.primary))
		}
		else
			other.secondary.filter(contains).map { TypeSet(_) }
	}
	
	def withPrimary(t: PokeType) = copy(primary = t)
	def withSecondary(t: PokeType) = copy(secondary = Some(t))
}