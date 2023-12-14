package vf.poke.companion.database.access.many.gameplay.poke_capture

import utopia.flow.generic.casting.ValueConversions._
import utopia.vault.database.Connection
import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.companion.database.factory.gameplay.PokeCaptureFactory
import vf.poke.companion.database.model.gameplay.PokeCaptureModel
import vf.poke.companion.model.stored.gameplay.PokeCapture

import java.time.Instant

object ManyPokeCapturesAccess
{
	// NESTED	--------------------
	
	private class ManyPokeCapturesSubView(condition: Condition) extends ManyPokeCapturesAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points which target multiple poke captures at a time
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
trait ManyPokeCapturesAccess 
	extends ManyRowModelAccess[PokeCapture] with FilterableView[ManyPokeCapturesAccess] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * run ids of the accessible poke captures
	  */
	def runIds(implicit connection: Connection) = pullColumn(model.runIdColumn).map { v => v.getInt }
	
	/**
	  * poke ids of the accessible poke captures
	  */
	def pokeIds(implicit connection: Connection) = pullColumn(model.pokeIdColumn).map { v => v.getInt }
	
	/**
	  * levels of the accessible poke captures
	  */
	def levels(implicit connection: Connection) = pullColumn(model.levelColumn).map { v => v.getInt }
	
	/**
	  * creation times of the accessible poke captures
	  */
	def creationTimes(implicit connection: Connection) = pullColumn(model.createdColumn)
		.map { v => v.getInstant }
	
	def ids(implicit connection: Connection) = pullColumn(index).map { v => v.getInt }
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = PokeCaptureModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PokeCaptureFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): ManyPokeCapturesAccess = 
		new ManyPokeCapturesAccess.ManyPokeCapturesSubView(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the creation times of the targeted poke captures
	  * @param newCreated A new created to assign
	  * @return Whether any poke capture was affected
	  */
	def creationTimes_=(newCreated: Instant)(implicit connection: Connection) = 
		putColumn(model.createdColumn, newCreated)
	
	/**
	  * Updates the levels of the targeted poke captures
	  * @param newLevel A new level to assign
	  * @return Whether any poke capture was affected
	  */
	def levels_=(newLevel: Int)(implicit connection: Connection) = putColumn(model.levelColumn, newLevel)
	
	/**
	  * Updates the poke ids of the targeted poke captures
	  * @param newPokeId A new poke id to assign
	  * @return Whether any poke capture was affected
	  */
	def pokeIds_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
	
	/**
	  * Updates the run ids of the targeted poke captures
	  * @param newRunId A new run id to assign
	  * @return Whether any poke capture was affected
	  */
	def runIds_=(newRunId: Int)(implicit connection: Connection) = putColumn(model.runIdColumn, newRunId)
}

