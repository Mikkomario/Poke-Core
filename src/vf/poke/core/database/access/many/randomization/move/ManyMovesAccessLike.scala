package vf.poke.core.database.access.many.randomization.move

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import vf.poke.core.database.model.randomization.MoveModel
import vf.poke.core.model.enumeration.{CriticalRate, MoveCategory, PokeType}

/**
  * A common trait for access points which target multiple moves or similar instances at a time
  * @author Mikko Hilpinen
  * @since 13.12.2023, v1.0
  */
trait ManyMovesAccessLike[+A, +Repr] extends ManyModelAccess[A] with Indexed with FilterableView[Repr]
{
	// COMPUTED	--------------------
	
	/**
	  * randomization ids of the accessible moves
	  */
	def randomizationIds(implicit connection: Connection) = 
		pullColumn(model.randomizationIdColumn).map { v => v.getInt }
	
	/**
	  * in game indices of the accessible moves
	  */
	def inGameIndices(implicit connection: Connection) = pullColumn(model.indexInGameColumn)
		.map { v => v.getInt }
	
	/**
	  * names of the accessible moves
	  */
	def names(implicit connection: Connection) = pullColumn(model.nameColumn).flatMap { _.string }
	
	/**
	  * move types of the accessible moves
	  */
	def moveTypes(implicit connection: Connection) = 
		pullColumn(model.moveTypeColumn).map { v => v.getInt }.flatMap(PokeType.findForId)
	
	/**
	  * categories of the accessible moves
	  */
	def categories(implicit connection: Connection) = 
		pullColumn(model.categoryColumn).flatMap { v => v.int }.flatMap(MoveCategory.findForId)
	
	/**
	  * damages of the accessible moves
	  */
	def damages(implicit connection: Connection) = pullColumn(model.damageColumn).map { v => v.getInt }
	
	/**
	  * hit counts of the accessible moves
	  */
	def hitCounts(implicit connection: Connection) = pullColumn(model.hitCountColumn).map { v => v.getDouble }
	
	/**
	  * hit ratios of the accessible moves
	  */
	def hitRatios(implicit connection: Connection) = pullColumn(model.hitRatioColumn).map { v => v.getDouble }
	
	/**
	  * critical rates of the accessible moves
	  */
	def criticalRates(implicit connection: Connection) = 
		pullColumn(model.criticalRateColumn).map { v => v.getInt }.flatMap(CriticalRate.findForId)
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = MoveModel
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the categories of the targeted moves
	  * @param newCategory A new category to assign
	  * @return Whether any move was affected
	  */
	def categories_=(newCategory: MoveCategory)(implicit connection: Connection) = 
		putColumn(model.categoryColumn, newCategory.id)
	
	/**
	  * Updates the critical rates of the targeted moves
	  * @param newCriticalRate A new critical rate to assign
	  * @return Whether any move was affected
	  */
	def criticalRates_=(newCriticalRate: CriticalRate)(implicit connection: Connection) = 
		putColumn(model.criticalRateColumn, newCriticalRate.id)
	
	/**
	  * Updates the damages of the targeted moves
	  * @param newDamage A new damage to assign
	  * @return Whether any move was affected
	  */
	def damages_=(newDamage: Int)(implicit connection: Connection) = putColumn(model.damageColumn, newDamage)
	
	/**
	  * Updates the hit counts of the targeted moves
	  * @param newHitCount A new hit count to assign
	  * @return Whether any move was affected
	  */
	def hitCounts_=(newHitCount: Double)(implicit connection: Connection) = 
		putColumn(model.hitCountColumn, newHitCount)
	
	/**
	  * Updates the hit ratios of the targeted moves
	  * @param newHitRatio A new hit ratio to assign
	  * @return Whether any move was affected
	  */
	def hitRatios_=(newHitRatio: Double)(implicit connection: Connection) = 
		putColumn(model.hitRatioColumn, newHitRatio)
	
	/**
	  * Updates the in game indices of the targeted moves
	  * @param newIndexInGame A new index in game to assign
	  * @return Whether any move was affected
	  */
	def inGameIndices_=(newIndexInGame: Int)(implicit connection: Connection) = 
		putColumn(model.indexInGameColumn, newIndexInGame)
	
	/**
	  * Updates the move types of the targeted moves
	  * @param newMoveType A new move type to assign
	  * @return Whether any move was affected
	  */
	def moveTypes_=(newMoveType: PokeType)(implicit connection: Connection) = 
		putColumn(model.moveTypeColumn, newMoveType.id)
	
	/**
	  * Updates the names of the targeted moves
	  * @param newName A new name to assign
	  * @return Whether any move was affected
	  */
	def names_=(newName: String)(implicit connection: Connection) = putColumn(model.nameColumn, newName)
	
	/**
	  * Updates the randomization ids of the targeted moves
	  * @param newRandomizationId A new randomization id to assign
	  * @return Whether any move was affected
	  */
	def randomizationIds_=(newRandomizationId: Int)(implicit connection: Connection) = 
		putColumn(model.randomizationIdColumn, newRandomizationId)
}

