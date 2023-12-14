package vf.poke.core.database.access.single.poke.ability

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import vf.poke.core.database.model.poke.PokeAbilityModel

/**
  * A common trait for access points which target individual poke abilities or similar items at a time
  * @author Mikko Hilpinen
  * @since 13.12.2023, v1.0
  */
trait UniquePokeAbilityAccessLike[+A] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the poke that may have this ability. None if no poke ability (or value) was found.
	  */
	def pokeId(implicit connection: Connection) = pullColumn(model.pokeIdColumn).int
	
	/**
	  * Id of the ability this poke may have. None if no poke ability (or value) was found.
	  */
	def abilityId(implicit connection: Connection) = pullColumn(model.abilityIdColumn).int
	
	/**
	  * True if this is a hidden ability. Hidden abilities may only be acquired 
	  * with special measures.. None if no poke ability (or value) was found.
	  */
	def isHidden(implicit connection: Connection) = pullColumn(model.isHiddenColumn).boolean
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = PokeAbilityModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the ability ids of the targeted poke abilities
	  * @param newAbilityId A new ability id to assign
	  * @return Whether any poke ability was affected
	  */
	def abilityId_=(newAbilityId: Int)(implicit connection: Connection) = 
		putColumn(model.abilityIdColumn, newAbilityId)
	
	/**
	  * Updates the are hidden of the targeted poke abilities
	  * @param newIsHidden A new is hidden to assign
	  * @return Whether any poke ability was affected
	  */
	def isHidden_=(newIsHidden: Boolean)(implicit connection: Connection) = 
		putColumn(model.isHiddenColumn, newIsHidden)
	
	/**
	  * Updates the poke ids of the targeted poke abilities
	  * @param newPokeId A new poke id to assign
	  * @return Whether any poke ability was affected
	  */
	def pokeId_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
}

