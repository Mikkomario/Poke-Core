package vf.poke.core.database.access.single.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import vf.poke.core.database.model.poke.PokeModel
import vf.poke.core.model.enumeration.PokeType

/**
  * A common trait for access points which target individual pokes or similar items at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait UniquePokeAccessLike[+A] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * The randomization in which this poke appears. None if no poke (or value) was found.
	  */
	def randomizationId(implicit connection: Connection) = pullColumn(model.randomizationIdColumn).int
	
	/**
	  * The pokedex-number of this poke in the game in which it appears. None if no poke (or value) was found.
	  */
	def number(implicit connection: Connection) = pullColumn(model.numberColumn).int
	
	/**
	  * 0-based index of the specific poke 
	  * "forme" represented by this instance. 0 is the default forme.. None if no poke (or value) was found.
	  */
	def formeIndex(implicit connection: Connection) = pullColumn(model.formeIndexColumn).int
	
	/**
	  * Name of this poke in the english translation / game data. None if no poke (or value) was found.
	  */
	def name(implicit connection: Connection) = pullColumn(model.nameColumn).getString
	
	/**
	  * The primary type of this poke. None if no poke (or value) was found.
	  */
	def primaryType(implicit connection: Connection) = 
		pullColumn(model.primaryTypeColumn).int.flatMap(PokeType.findForId)
	
	/**
	  * 
		The secondary type of this poke. None if this poke only has one type. None if no poke (or value) was found.
	  */
	def secondaryType(implicit connection: Connection) = 
		pullColumn(model.secondaryTypeColumn).int.flatMap(PokeType.findForId)
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = PokeModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the forme indexs of the targeted pokes
	  * @param newFormeIndex A new forme index to assign
	  * @return Whether any poke was affected
	  */
	def formeIndex_=(newFormeIndex: Int)(implicit connection: Connection) = 
		putColumn(model.formeIndexColumn, newFormeIndex)
	
	/**
	  * Updates the names of the targeted pokes
	  * @param newName A new name to assign
	  * @return Whether any poke was affected
	  */
	def name_=(newName: String)(implicit connection: Connection) = putColumn(model.nameColumn, newName)
	
	/**
	  * Updates the numbers of the targeted pokes
	  * @param newNumber A new number to assign
	  * @return Whether any poke was affected
	  */
	def number_=(newNumber: Int)(implicit connection: Connection) = putColumn(model.numberColumn, newNumber)
	
	/**
	  * Updates the primary types of the targeted pokes
	  * @param newPrimaryType A new primary type to assign
	  * @return Whether any poke was affected
	  */
	def primaryType_=(newPrimaryType: PokeType)(implicit connection: Connection) = 
		putColumn(model.primaryTypeColumn, newPrimaryType.id)
	
	/**
	  * Updates the randomization ids of the targeted pokes
	  * @param newRandomizationId A new randomization id to assign
	  * @return Whether any poke was affected
	  */
	def randomizationId_=(newRandomizationId: Int)(implicit connection: Connection) = 
		putColumn(model.randomizationIdColumn, newRandomizationId)
	
	/**
	  * Updates the secondary types of the targeted pokes
	  * @param newSecondaryType A new secondary type to assign
	  * @return Whether any poke was affected
	  */
	def secondaryType_=(newSecondaryType: PokeType)(implicit connection: Connection) = 
		putColumn(model.secondaryTypeColumn, newSecondaryType.id)
}

