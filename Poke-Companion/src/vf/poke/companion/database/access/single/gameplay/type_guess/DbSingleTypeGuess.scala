package vf.poke.companion.database.access.single.gameplay.type_guess

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.companion.model.stored.gameplay.TypeGuess

/**
  * An access point to individual type guessses, based on their id
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class DbSingleTypeGuess(id: Int) extends UniqueTypeGuessAccess with SingleIntIdModelAccess[TypeGuess]

