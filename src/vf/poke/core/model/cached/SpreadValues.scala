package vf.poke.core.model.cached

/**
 * Contains values associated with certain spread categories / segments
 * @author Mikko Hilpinen
 * @since 11.12.2023, v1.0-alt
 */
case class SpreadValues[+A](low: A, midLow: A, midHigh: A, high: A)
