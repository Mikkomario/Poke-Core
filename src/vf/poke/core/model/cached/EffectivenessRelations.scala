package vf.poke.core.model.cached

import utopia.flow.collection.immutable.caching.cache.Cache
import vf.poke.core.model.enumeration.PokeType

object EffectivenessRelations
{
	// ATTRIBUTES   -------------------------
	
	// The first keys are defending types
	// The second keys are offensive types
	// The values are effectiveness levels
	private val cache = Cache { against: TypeSet =>
		PokeType.values.map { t => t -> t.effectivenessAgainst(against) }.toMap
	}
	private val relationsCache = Cache { t: TypeSet => new EffectivenessRelations(t) }
	
	lazy val averageSingleTypeDefenseRating =
		PokeType.values.iterator.map { apply(_).defenseRating }.sum / PokeType.values.length
	lazy val averageSingleTypeOffenseRating =
		PokeType.values.iterator.map { apply(_).offenseRating }.sum / PokeType.values.length
	
	
	// OTHER    --------------------------
	
	def apply(t: PokeType): EffectivenessRelations = apply(TypeSet(t))
	def apply(t: TypeSet): EffectivenessRelations = relationsCache(t)
	
	private def offenseValueOf(effectiveness: Double) = {
		if (effectiveness > 3.0)
			3.0
		else if (effectiveness > 1.0)
			1.0
		else if (effectiveness <= 0.0)
			-3.0
		else if (effectiveness < 0.5)
			-1.5
		else if (effectiveness < 1.0)
			-1.0
		else
			0.0
	}
	private def defenseValueOf(effectiveness: Double) = -offenseValueOf(effectiveness)
}

/**
 * Represents the type effectiveness -relationships of some type
 * @author Mikko Hilpinen
 * @since 28.11.2023, v1.0-alt
 */
case class EffectivenessRelations(types: TypeSet)
{
	import EffectivenessRelations._
	
	// ATTRIBUTES   ------------------------
	
	/**
	 * Overall defensive power of this type combo, where 0 is neutral, negative is weak and positive is strong
	 */
	lazy val defenseRating = cache(types).valuesIterator.map(defenseValueOf).sum
	/**
	 * Overall defensive power of this type combo, where 0 is neutral, negative is weak and positive is strong
	 */
	lazy val offenseRating = PokeType.values.iterator.map { defendingType =>
		val map = cache(TypeSet(defendingType))
		types.types.map { attackingType => offenseValueOf(map(attackingType)) }.max
	}.sum
	
	lazy val relativeDefenseRating = defenseRating / averageSingleTypeDefenseRating
	lazy val relativeOffenseRating = offenseRating / averageSingleTypeOffenseRating
	
	lazy val defensiveWeaknesses = PokeType.values.iterator.filter { defenseRatingAgainst(_) < 0 }.toSet
	lazy val offensiveWeaknesses = PokeType.values.iterator.filter { offenseRatingAgainst(_) < 0 }.toSet
	
	
	// OTHER    --------------------------
	
	def defenseRatingAgainst(damageType: PokeType): Double = defenseValueOf(cache(types)(damageType))
	def defenseRatingAgainst(other: TypeSet): Double = other.types.map(defenseRatingAgainst).min
	
	def offenseRatingAgainst(target: PokeType): Double = offenseRatingAgainst(TypeSet(target))
	def offenseRatingAgainst(targetType: TypeSet) =
		types.types.map { t => offenseValueOf(cache(targetType)(t)) }.max
	
	/**
	 * @param other Opposing type
	 * @return Combined effectiveness based on offense and defense
	 */
	def against(other: TypeSet) = defenseRatingAgainst(other) + offenseRatingAgainst(other)
}
