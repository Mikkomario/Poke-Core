package vf.poke.core.database.access.many.poke.ability

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.poke.core.database.model.poke.PokeAbilityModel

/**
  * A common trait for access points which target multiple poke abilities or similar instances at a time
  * @author Mikko Hilpinen
  * @since 13.12.2023, v1.0
  */
trait ManyPokeAbilitiesAccessLike[+A, +Repr] extends ManyModelAccess[A] with Indexed with FilterableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * poke ids of the accessible poke abilities
	  */
	def pokeIds(implicit connection: Connection) = pullColumn(model.pokeIdColumn).map { v => v.getInt }
	
	/**
	  * ability ids of the accessible poke abilities
	  */
	def abilityIds(implicit connection: Connection) = pullColumn(model.abilityIdColumn).map { v => v.getInt }
	
	/**
	  * are hidden of the accessible poke abilities
	  */
	def areHidden(implicit connection: Connection) = pullColumn(model.isHiddenColumn)
		.map { v => v.getBoolean }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = PokeAbilityModel
	
	/**
	 * @return Copy of this access limited to normal (non-hidden) abilities
	 */
	def normal = filter(model.withIsHidden(false).toCondition)
	
	
	// OTHER	--------------------
	
	/**
	 * @param pokeId Id of the targeted poke
	 * @return Access to that poke's abilities
	 */
	def ofPoke(pokeId: Int) = filter(model.withPokeId(pokeId).toCondition)
	/**
	 * @param pokeIds Ids of the targeted pokes
	 * @return Access to abilities of those pokes
	 */
	def ofPokes(pokeIds: Iterable[Int]) = filter(model.pokeIdColumn.in(pokeIds))
	
	/**
	  * Updates the ability ids of the targeted poke abilities
	  * @param newAbilityId A new ability id to assign
	  * @return Whether any poke ability was affected
	  */
	def abilityIds_=(newAbilityId: Int)(implicit connection: Connection) = 
		putColumn(model.abilityIdColumn, newAbilityId)
	
	/**
	  * Updates the are hidden of the targeted poke abilities
	  * @param newIsHidden A new is hidden to assign
	  * @return Whether any poke ability was affected
	  */
	def areHidden_=(newIsHidden: Boolean)(implicit connection: Connection) = 
		putColumn(model.isHiddenColumn, newIsHidden)
	
	/**
	  * Updates the poke ids of the targeted poke abilities
	  * @param newPokeId A new poke id to assign
	  * @return Whether any poke ability was affected
	  */
	def pokeIds_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
}

