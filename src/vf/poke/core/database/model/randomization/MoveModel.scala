package vf.poke.core.database.model.randomization

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.StorableWithFactory
import utopia.vault.nosql.storable.DataInserter
import vf.poke.core.database.factory.randomization.MoveFactory
import vf.poke.core.model.enumeration.{CriticalRate, MoveCategory, PokeType}
import vf.poke.core.model.partial.randomization.MoveData
import vf.poke.core.model.stored.randomization.Move

/**
  * Used for constructing MoveModel instances and for inserting moves to the database
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object MoveModel extends DataInserter[MoveModel, Move, MoveData]
{
	// ATTRIBUTES	--------------------
	
	/**
	  * Name of the property that contains move randomization id
	  */
	val randomizationIdAttName = "randomizationId"
	
	/**
	  * Name of the property that contains move index in game
	  */
	val indexInGameAttName = "indexInGame"
	
	/**
	  * Name of the property that contains move name
	  */
	val nameAttName = "name"
	
	/**
	  * Name of the property that contains move move type
	  */
	val moveTypeAttName = "moveTypeId"
	
	/**
	  * Name of the property that contains move category
	  */
	val categoryAttName = "categoryId"
	
	/**
	  * Name of the property that contains move damage
	  */
	val damageAttName = "damage"
	
	/**
	  * Name of the property that contains move hit count
	  */
	val hitCountAttName = "hitCount"
	
	/**
	  * Name of the property that contains move hit ratio
	  */
	val hitRatioAttName = "hitRatio"
	
	/**
	  * Name of the property that contains move critical rate
	  */
	val criticalRateAttName = "criticalRateId"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Column that contains move randomization id
	  */
	def randomizationIdColumn = table(randomizationIdAttName)
	
	/**
	  * Column that contains move index in game
	  */
	def indexInGameColumn = table(indexInGameAttName)
	
	/**
	  * Column that contains move name
	  */
	def nameColumn = table(nameAttName)
	
	/**
	  * Column that contains move move type
	  */
	def moveTypeColumn = table(moveTypeAttName)
	
	/**
	  * Column that contains move category
	  */
	def categoryColumn = table(categoryAttName)
	
	/**
	  * Column that contains move damage
	  */
	def damageColumn = table(damageAttName)
	
	/**
	  * Column that contains move hit count
	  */
	def hitCountColumn = table(hitCountAttName)
	
	/**
	  * Column that contains move hit ratio
	  */
	def hitRatioColumn = table(hitRatioAttName)
	
	/**
	  * Column that contains move critical rate
	  */
	def criticalRateColumn = table(criticalRateAttName)
	
	/**
	  * The factory object used by this model type
	  */
	def factory = MoveFactory
	
	
	// IMPLEMENTED	--------------------
	
	override def table = factory.table
	
	override def apply(data: MoveData) = 
		apply(None, Some(data.randomizationId), Some(data.indexInGame), data.name, Some(data.moveType.id), 
			data.category.map { e => e.id }, Some(data.damage), Some(data.hitCount), Some(data.hitRatio), 
			Some(data.criticalRate.id))
	
	override protected def complete(id: Value, data: MoveData) = Move(id.getInt, data)
	
	
	// OTHER	--------------------
	
	/**
	  * @param category Category of this move, whether physical or special. None if neither.
	  * @return A model containing only the specified category
	  */
	def withCategory(category: MoveCategory) = apply(category = Some(category.id))
	
	/**
	  * @param criticalRate Rate of critical hits applied to this move
	  * @return A model containing only the specified critical rate
	  */
	def withCriticalRate(criticalRate: CriticalRate) = apply(criticalRate = Some(criticalRate.id))
	
	/**
	  * @param damage The amount of damage inflicted by this move, in game units
	  * @return A model containing only the specified damage
	  */
	def withDamage(damage: Int) = apply(damage = Some(damage))
	
	/**
	  * @param hitCount The (average) hit count of this move
	  * @return A model containing only the specified hit count
	  */
	def withHitCount(hitCount: Double) = apply(hitCount = Some(hitCount))
	
	/**
	  * @param hitRatio The ratio of how often this move hits, 
		where 100 is 100% hit without accuracy or evasion mods
	  * @return A model containing only the specified hit ratio
	  */
	def withHitRatio(hitRatio: Double) = apply(hitRatio = Some(hitRatio))
	
	/**
	  * @param id A move id
	  * @return A model with that id
	  */
	def withId(id: Int) = apply(Some(id))
	
	/**
	  * @param indexInGame Index of this move in the game data
	  * @return A model containing only the specified index in game
	  */
	def withIndexInGame(indexInGame: Int) = apply(indexInGame = Some(indexInGame))
	
	/**
	  * @param moveType Type of this move
	  * @return A model containing only the specified move type
	  */
	def withMoveType(moveType: PokeType) = apply(moveType = Some(moveType.id))
	
	/**
	  * @param name Name of this move in the game
	  * @return A model containing only the specified name
	  */
	def withName(name: String) = apply(name = name)
	
	/**
	  * @param randomizationId Id of the randomization where these details apply
	  * @return A model containing only the specified randomization id
	  */
	def withRandomizationId(randomizationId: Int) = apply(randomizationId = Some(randomizationId))
}

/**
  * Used for interacting with Moves in the database
  * @param id move database id
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
case class MoveModel(id: Option[Int] = None, randomizationId: Option[Int] = None, 
	indexInGame: Option[Int] = None, name: String = "", moveType: Option[Int] = None, 
	category: Option[Int] = None, damage: Option[Int] = None, hitCount: Option[Double] = None, 
	hitRatio: Option[Double] = None, criticalRate: Option[Int] = None) 
	extends StorableWithFactory[Move]
{
	// IMPLEMENTED	--------------------
	
	override def factory = MoveModel.factory
	
	override def valueProperties = {
		import MoveModel._
		Vector("id" -> id, randomizationIdAttName -> randomizationId, indexInGameAttName -> indexInGame, 
			nameAttName -> name, moveTypeAttName -> moveType, categoryAttName -> category, 
			damageAttName -> damage, hitCountAttName -> hitCount, hitRatioAttName -> hitRatio, 
			criticalRateAttName -> criticalRate)
	}
	
	
	// OTHER	--------------------
	
	/**
	  * @param category Category of this move, whether physical or special. None if neither.
	  * @return A new copy of this model with the specified category
	  */
	def withCategory(category: MoveCategory) = copy(category = Some(category.id))
	
	/**
	  * @param criticalRate Rate of critical hits applied to this move
	  * @return A new copy of this model with the specified critical rate
	  */
	def withCriticalRate(criticalRate: CriticalRate) = copy(criticalRate = Some(criticalRate.id))
	
	/**
	  * @param damage The amount of damage inflicted by this move, in game units
	  * @return A new copy of this model with the specified damage
	  */
	def withDamage(damage: Int) = copy(damage = Some(damage))
	
	/**
	  * @param hitCount The (average) hit count of this move
	  * @return A new copy of this model with the specified hit count
	  */
	def withHitCount(hitCount: Double) = copy(hitCount = Some(hitCount))
	
	/**
	  * @param hitRatio The ratio of how often this move hits, 
		where 100 is 100% hit without accuracy or evasion mods
	  * @return A new copy of this model with the specified hit ratio
	  */
	def withHitRatio(hitRatio: Double) = copy(hitRatio = Some(hitRatio))
	
	/**
	  * @param indexInGame Index of this move in the game data
	  * @return A new copy of this model with the specified index in game
	  */
	def withIndexInGame(indexInGame: Int) = copy(indexInGame = Some(indexInGame))
	
	/**
	  * @param moveType Type of this move
	  * @return A new copy of this model with the specified move type
	  */
	def withMoveType(moveType: PokeType) = copy(moveType = Some(moveType.id))
	
	/**
	  * @param name Name of this move in the game
	  * @return A new copy of this model with the specified name
	  */
	def withName(name: String) = copy(name = name)
	
	/**
	  * @param randomizationId Id of the randomization where these details apply
	  * @return A new copy of this model with the specified randomization id
	  */
	def withRandomizationId(randomizationId: Int) = copy(randomizationId = Some(randomizationId))
}

