package vf.poke.core.model.cached

object SpreadThresholds2
{
	// NB: Assumes a pre-sorted collection
	def from[A](coll: Seq[A], thresholdCount: Int = 3)(implicit ord: Ordering[A]) = {
		val s = coll.size
		val thresholds = (1 to thresholdCount).map { i => s * i / (thresholdCount + 1) }
		apply(thresholds)
	}
	
	def apply[A](t1: A, t2: A, more: A*)(implicit ord: Ordering[A]) = new SpreadThresholds2[A](Vector(t1, t2) ++ more)
}

/**
 * Represents the spread of values around certain thresholds
 * @author Mikko Hilpinen
 * @since 11.12.2023, v1.0-alt
 */
case class SpreadThresholds2[A](thresholds: Seq[A])(implicit ord: Ordering[A])
{
	def +[B](values: SpreadValues2[B]) = Spread2(this, values)
	
	def map[B](f: A => B)(implicit ord: Ordering[B]) = SpreadThresholds2(thresholds.map(f))
}