package vf.poke.core.model.cached

import scala.math.Ordered.orderingToOrdered

/**
 * Combines Spread thresholds and values
 * @author Mikko Hilpinen
 * @since 11.12.2023, v1.0-alt
 */
case class Spread[K, +V](thresholds: SpreadThresholds[K], values: SpreadValues[V])(implicit ord: Ordering[K])
{
	def apply(key: K) = {
		if (key < thresholds.low)
			values.low
		else if (key < thresholds.average)
			values.midLow
		else if (key < thresholds.high)
			values.midHigh
		else
			values.high
	}
}