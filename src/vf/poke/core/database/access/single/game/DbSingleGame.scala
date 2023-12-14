package vf.poke.core.database.access.single.game

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.core.database.access.single.randomization.DbRandomization
import vf.poke.core.model.stored.game.Game

/**
  * An access point to individual games, based on their id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class DbSingleGame(id: Int) extends UniqueGameAccess with SingleIntIdModelAccess[Game]
{
	/**
	 * @return Access to the unmodified state of this game
	 */
	def unmodified = DbRandomization.original(id)
}