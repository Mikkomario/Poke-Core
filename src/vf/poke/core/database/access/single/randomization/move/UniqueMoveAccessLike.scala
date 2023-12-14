package vf.poke.core.database.access.single.randomization.move

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import vf.poke.core.database.model.randomization.MoveModel
import vf.poke.core.model.enumeration.{CriticalRate, MoveCategory, PokeType}

/**
  * A common trait for access points which target individual moves or similar items at a time
  * @author Mikko Hilpinen
  * @since 13.12.2023, v1.0
  */
trait UniqueMoveAccessLike[+A] 
	extends SingleModelAccess[A] with DistinctModelAccess[A, Option[A], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the randomization where these details apply. None if no move (or value) was found.
	  */
	def randomizationId(implicit connection: Connection) = pullColumn(model.randomizationIdColumn).int
	
	/**
	  * Index of this move in the game data. None if no move (or value) was found.
	  */
	def indexInGame(implicit connection: Connection) = pullColumn(model.indexInGameColumn).int
	
	/**
	  * Name of this move in the game. None if no move (or value) was found.
	  */
	def name(implicit connection: Connection) = pullColumn(model.nameColumn).getString
	
	/**
	  * Type of this move. None if no move (or value) was found.
	  */
	def moveType(implicit connection: Connection) = 
		pullColumn(model.moveTypeColumn).int.flatMap(PokeType.findForId)
	
	/**
	  * Category of this move, 
		whether physical or special. None if neither.. None if no move (or value) was found.
	  */
	def category(implicit connection: Connection) = 
		pullColumn(model.categoryColumn).int.flatMap(MoveCategory.findForId)
	
	/**
	  * The amount of damage inflicted by this move, in game units. None if no move (or value) was found.
	  */
	def damage(implicit connection: Connection) = pullColumn(model.damageColumn).int
	
	/**
	  * The (average) hit count of this move. None if no move (or value) was found.
	  */
	def hitCount(implicit connection: Connection) = pullColumn(model.hitCountColumn).double
	
	/**
	  * The ratio of how often this move hits, 
	  * where 100 is 100% hit without accuracy or evasion mods. None if no move (or value) was found.
	  */
	def hitRatio(implicit connection: Connection) = pullColumn(model.hitRatioColumn).double
	
	/**
	  * Rate of critical hits applied to this move. None if no move (or value) was found.
	  */
	def criticalRate(implicit connection: Connection) = 
		pullColumn(model.criticalRateColumn).int.flatMap(CriticalRate.findForId)
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
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
	def category_=(newCategory: MoveCategory)(implicit connection: Connection) = 
		putColumn(model.categoryColumn, newCategory.id)
	
	/**
	  * Updates the critical rates of the targeted moves
	  * @param newCriticalRate A new critical rate to assign
	  * @return Whether any move was affected
	  */
	def criticalRate_=(newCriticalRate: CriticalRate)(implicit connection: Connection) = 
		putColumn(model.criticalRateColumn, newCriticalRate.id)
	
	/**
	  * Updates the damages of the targeted moves
	  * @param newDamage A new damage to assign
	  * @return Whether any move was affected
	  */
	def damage_=(newDamage: Int)(implicit connection: Connection) = putColumn(model.damageColumn, newDamage)
	
	/**
	  * Updates the hit counts of the targeted moves
	  * @param newHitCount A new hit count to assign
	  * @return Whether any move was affected
	  */
	def hitCount_=(newHitCount: Double)(implicit connection: Connection) = 
		putColumn(model.hitCountColumn, newHitCount)
	
	/**
	  * Updates the hit ratios of the targeted moves
	  * @param newHitRatio A new hit ratio to assign
	  * @return Whether any move was affected
	  */
	def hitRatio_=(newHitRatio: Double)(implicit connection: Connection) = 
		putColumn(model.hitRatioColumn, newHitRatio)
	
	/**
	  * Updates the in game indices of the targeted moves
	  * @param newIndexInGame A new index in game to assign
	  * @return Whether any move was affected
	  */
	def indexInGame_=(newIndexInGame: Int)(implicit connection: Connection) = 
		putColumn(model.indexInGameColumn, newIndexInGame)
	
	/**
	  * Updates the move types of the targeted moves
	  * @param newMoveType A new move type to assign
	  * @return Whether any move was affected
	  */
	def moveType_=(newMoveType: PokeType)(implicit connection: Connection) = 
		putColumn(model.moveTypeColumn, newMoveType.id)
	
	/**
	  * Updates the names of the targeted moves
	  * @param newName A new name to assign
	  * @return Whether any move was affected
	  */
	def name_=(newName: String)(implicit connection: Connection) = putColumn(model.nameColumn, newName)
	
	/**
	  * Updates the randomization ids of the targeted moves
	  * @param newRandomizationId A new randomization id to assign
	  * @return Whether any move was affected
	  */
	def randomizationId_=(newRandomizationId: Int)(implicit connection: Connection) = 
		putColumn(model.randomizationIdColumn, newRandomizationId)
}

