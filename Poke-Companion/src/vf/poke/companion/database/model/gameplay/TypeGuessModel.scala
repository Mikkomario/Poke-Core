package vf.poke.companion.database.model.gameplay

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import utopia.vault.nosql.storable.deprecation.DeprecatableAfter
import vf.poke.companion.database.factory.gameplay.TypeGuessFactory
import vf.poke.companion.model.partial.gameplay.TypeGuessData
import vf.poke.companion.model.stored.gameplay.TypeGuess
import vf.poke.core.model.enumeration.PokeType

import java.time.Instant

/**
  * Used for constructing TypeGuessModel instances and for inserting type guessses to the database
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
object TypeGuessModel 
	extends DataInserter[TypeGuessModel, TypeGuess, TypeGuessData] with DeprecatableAfter[TypeGuessModel]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains type guess run id
	  */
	val runIdAttName = "runId"
	
	/**
	  * Name of the property that contains type guess poke id
	  */
	val pokeIdAttName = "pokeId"
	
	/**
	  * Name of the property that contains type guess guessed type
	  */
	val guessedTypeAttName = "guessedTypeId"
	
	/**
	  * Name of the property that contains type guess created
	  */
	val createdAttName = "created"
	
	/**
	  * Name of the property that contains type guess deprecated after
	  */
	val deprecatedAfterAttName = "deprecatedAfter"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains type guess run id
	  */
	def runIdColumn = table(runIdAttName)
	
	/**
	  * Column that contains type guess poke id
	  */
	def pokeIdColumn = table(pokeIdAttName)
	
	/**
	  * Column that contains type guess guessed type
	  */
	def guessedTypeColumn = table(guessedTypeAttName)
	
	/**
	  * Column that contains type guess created
	  */
	def createdColumn = table(createdAttName)
	
	/**
	  * Column that contains type guess deprecated after
	  */
	def deprecatedAfterColumn = table(deprecatedAfterAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = TypeGuessFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: TypeGuessData) = 
		apply(None, Some(data.runId), Some(data.pokeId), Some(data.guessedType.id), Some(data.created), 
			data.deprecatedAfter)
	
	override protected def complete(id: Value, data: TypeGuessData) = TypeGuess(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param created Time when this type guess was added to the database
	  * @return A model containing only the specified created
	  */
	def withCreated(created: Instant) = apply(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when this guess was replaced or when it became invalid
	  * @return A model containing only the specified deprecated after
	  */
	def withDeprecatedAfter(deprecatedAfter: Instant) = apply(deprecatedAfter = Some(deprecatedAfter))
	
	/**
	  * @param guessedType Poke type of this type guess
	  * @return A model containing only the specified guessed type
	  */
	def withGuessedType(guessedType: PokeType) = apply(guessedType = Some(guessedType.id))
	
	/**
	  * @param id A type guess id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param pokeId Id of the described poke
	  * @return A model containing only the specified poke id
	  */
	def withPokeId(pokeId: Int) = apply(pokeId = Some(pokeId))
	
	/**
	  * @param runId Id of the game run on which this guess was made
	  * @return A model containing only the specified run id
	  */
	def withRunId(runId: Int) = apply(runId = Some(runId))
}

/**
  * Used for interacting with TypeGuessses in the database
  * @param id type guess database id
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class TypeGuessModel(id: Option[Int] = None, runId: Option[Int] = None, pokeId: Option[Int] = None, 
	guessedType: Option[Int] = None, created: Option[Instant] = None, 
	deprecatedAfter: Option[Instant] = None) 
	extends StorableWithFactory[TypeGuess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = TypeGuessModel.factory
	
	override def valueProperties = {
		import TypeGuessModel._
		Vector("id" -> id, runIdAttName -> runId, pokeIdAttName -> pokeId, guessedTypeAttName -> guessedType, 
			createdAttName -> created, deprecatedAfterAttName -> deprecatedAfter)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param created Time when this type guess was added to the database
	  * @return A new copy of this model with the specified created
	  */
	def withCreated(created: Instant) = copy(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when this guess was replaced or when it became invalid
	  * @return A new copy of this model with the specified deprecated after
	  */
	def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
	
	/**
	  * @param guessedType Poke type of this type guess
	  * @return A new copy of this model with the specified guessed type
	  */
	def withGuessedType(guessedType: PokeType) = copy(guessedType = Some(guessedType.id))
	
	/**
	  * @param pokeId Id of the described poke
	  * @return A new copy of this model with the specified poke id
	  */
	def withPokeId(pokeId: Int) = copy(pokeId = Some(pokeId))
	
	/**
	  * @param runId Id of the game run on which this guess was made
	  * @return A new copy of this model with the specified run id
	  */
	def withRunId(runId: Int) = copy(runId = Some(runId))
}

