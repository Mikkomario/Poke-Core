package vf.poke.companion.model.stored.gameplay

import utopia.vault.model.template.StoredModelConvertible
import vf.poke.companion.database.access.single.gameplay.type_guess.DbSingleTypeGuess
import vf.poke.companion.model.partial.gameplay.TypeGuessData

/**
  * Represents a type guess that has already been stored in the database
  * @param id id of this type guess in the database
  * @param data Wrapped type guess data
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class TypeGuess(id: Int, data: TypeGuessData) extends StoredModelConvertible[TypeGuessData]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this type guess in the database
	  */
	def access = DbSingleTypeGuess(id)
}

