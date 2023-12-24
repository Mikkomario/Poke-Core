package vf.poke.core.model.cached

import utopia.flow.collection.CollectionExtensions._

import scala.math.Ordered.orderingToOrdered

/**
 * Combines Spread thresholds and values
 * @author Mikko Hilpinen
 * @since 11.12.2023, v1.0-alt
 */
case class Spread2[K, +V](thresholds: SpreadThresholds2[K], values: SpreadValues2[V])(implicit ord: Ordering[K])
{
	def apply(key: K) = {
		thresholds.thresholds.findIndexWhere { key < _ } match {
			case Some(index) => values.values.lift(index).getOrElse(values.values.last)
			case None => values.values.last
		}
	}
}