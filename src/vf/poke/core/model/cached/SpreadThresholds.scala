package vf.poke.core.model.cached

@deprecated("Replaced with a new version", "v1.0-alt")
object SpreadThresholds
{
	// NB: Assumes a pre-sorted collection
	def from[A](coll: Seq[A])(implicit ord: Ordering[A]) = {
		val avg = coll(coll.size / 2)
		val low = coll(coll.size / 4)
		val high = coll(coll.size * 3 / 4)
		apply(low, avg, high)
	}
}

/**
 * Represents the spread of values around certain thresholds
 * @author Mikko Hilpinen
 * @since 11.12.2023, v1.0-alt
 */
@deprecated("Replaced with a new version", "v1.0-alt")
case class SpreadThresholds[A](low: A, average: A, high: A)(implicit ord: Ordering[A])
{
	def +[B](values: SpreadValues[B]) = Spread(this, values)
	
	def map[B](f: A => B)(implicit ord: Ordering[B]) =
		SpreadThresholds(f(low), f(average), f(high))
}