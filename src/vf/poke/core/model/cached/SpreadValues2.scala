package vf.poke.core.model.cached

object SpreadValues2
{
	def apply[A](v1: A, v2: A, more: A*) = new SpreadValues2[A](Vector(v1, v2) ++ more)
}

/**
 * Contains values associated with certain spread categories / segments
 * @author Mikko Hilpinen
 * @since 11.12.2023, v1.0-alt
 */
case class SpreadValues2[+A](values: Seq[A])
