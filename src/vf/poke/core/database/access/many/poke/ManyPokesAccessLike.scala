package vf.poke.core.database.access.many.poke

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.poke.core.database.model.poke.PokeModel
import vf.poke.core.model.enumeration.PokeType

/**
  * A common trait for access points which target multiple pokes or similar instances at a time
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
trait ManyPokesAccessLike[+A, +Repr] extends ManyModelAccess[A] with Indexed with FilterableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * randomization ids of the accessible pokes
	  */
	def randomizationIds(implicit connection: Connection) = 
		pullColumn(model.randomizationIdColumn).map { v => v.getInt }
	
	/**
	  * numbers of the accessible pokes
	  */
	def numbers(implicit connection: Connection) = pullColumn(model.numberColumn).map { v => v.getInt }
	
	/**
	  * forme indexs of the accessible pokes
	  */
	def formeIndexs(implicit connection: Connection) = pullColumn(model.formeIndexColumn)
		.map { v => v.getInt }
	
	/**
	  * names of the accessible pokes
	  */
	def names(implicit connection: Connection) = pullColumn(model.nameColumn).flatMap { _.string }
	
	/**
	  * primary types of the accessible pokes
	  */
	def primaryTypes(implicit connection: Connection) = 
		pullColumn(model.primaryTypeColumn).map { v => v.getInt }.flatMap(PokeType.findForId)
	
	/**
	  * secondary types of the accessible pokes
	  */
	def secondaryTypes(implicit connection: Connection) = 
		pullColumn(model.secondaryTypeColumn).flatMap { v => v.int }.flatMap(PokeType.findForId)
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = PokeModel
	
	
	// OTHER	--------------------
	
	/**
	 * @param randomizationId Id of the targeted randomization
	 * @return Access to pokes in that randomization
	 */
	def inRandomization(randomizationId: Int) = filter(model.withRandomizationId(randomizationId).toCondition)
	
	/**
	 * @param name A poke name or part of a poke name
	 * @return Access to pokes with that name
	 */
	def withNameLike(name: String) = filter(model.nameColumn.contains(name))
	
	/**
	  * Updates the forme indexs of the targeted pokes
	  * @param newFormeIndex A new forme index to assign
	  * @return Whether any poke was affected
	  */
	def formeIndexs_=(newFormeIndex: Int)(implicit connection: Connection) = 
		putColumn(model.formeIndexColumn, newFormeIndex)
	
	/**
	  * Updates the names of the targeted pokes
	  * @param newName A new name to assign
	  * @return Whether any poke was affected
	  */
	def names_=(newName: String)(implicit connection: Connection) = putColumn(model.nameColumn, newName)
	
	/**
	  * Updates the numbers of the targeted pokes
	  * @param newNumber A new number to assign
	  * @return Whether any poke was affected
	  */
	def numbers_=(newNumber: Int)(implicit connection: Connection) = putColumn(model.numberColumn, newNumber)
	
	/**
	  * Updates the primary types of the targeted pokes
	  * @param newPrimaryType A new primary type to assign
	  * @return Whether any poke was affected
	  */
	def primaryTypes_=(newPrimaryType: PokeType)(implicit connection: Connection) = 
		putColumn(model.primaryTypeColumn, newPrimaryType.id)
	
	/**
	  * Updates the randomization ids of the targeted pokes
	  * @param newRandomizationId A new randomization id to assign
	  * @return Whether any poke was affected
	  */
	def randomizationIds_=(newRandomizationId: Int)(implicit connection: Connection) = 
		putColumn(model.randomizationIdColumn, newRandomizationId)
	
	/**
	  * Updates the secondary types of the targeted pokes
	  * @param newSecondaryType A new secondary type to assign
	  * @return Whether any poke was affected
	  */
	def secondaryTypes_=(newSecondaryType: PokeType)(implicit connection: Connection) = 
		putColumn(model.secondaryTypeColumn, newSecondaryType.id)
}

