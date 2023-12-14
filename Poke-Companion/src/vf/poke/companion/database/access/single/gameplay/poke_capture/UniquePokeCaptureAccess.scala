package vf.poke.companion.database.access.single.gameplay.poke_capture

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.database.Connection
import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.access.template.model.DistinctModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.FilterableView
import utopia.vault.sql.Condition
import vf.poke.companion.database.factory.gameplay.PokeCaptureFactory
import vf.poke.companion.database.model.gameplay.PokeCaptureModel
import vf.poke.companion.model.stored.gameplay.PokeCapture

import java.time.Instant

object UniquePokeCaptureAccess
{
	// OTHER	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	def apply(condition: Condition): UniquePokeCaptureAccess = new _UniquePokeCaptureAccess(condition)
	
	
	// NESTED	--------------------
	
	private class _UniquePokeCaptureAccess(condition: Condition) extends UniquePokeCaptureAccess
	{
		// IMPLEMENTED	--------------------
		
		override def accessCondition = Some(condition)
	}
}

/**
  * A common trait for access points that return individual and distinct poke captures.
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
trait UniquePokeCaptureAccess 
	extends SingleRowModelAccess[PokeCapture] with FilterableView[UniquePokeCaptureAccess] 
		with DistinctModelAccess[PokeCapture, Option[PokeCapture], Value] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Id of the game run on which this capture was made. None if no poke capture (or value) was found.
	  */
	def runId(implicit connection: Connection) = pullColumn(model.runIdColumn).int
	
	/**
	  * Id of the captured poke. None if no poke capture (or value) was found.
	  */
	def pokeId(implicit connection: Connection) = pullColumn(model.pokeIdColumn).int
	
	/**
	  * Level at which this capture was made. None if no poke capture (or value) was found.
	  */
	def level(implicit connection: Connection) = pullColumn(model.levelColumn).int
	
	/**
	  * Time when this poke capture was added to the database. None if no poke capture (or value) was found.
	  */
	def created(implicit connection: Connection) = pullColumn(model.createdColumn).instant
	
	def id(implicit connection: Connection) = pullColumn(index).int
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = PokeCaptureModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PokeCaptureFactory
	
	override protected def self = this
	
	override def filter(filterCondition: Condition): UniquePokeCaptureAccess = 
		new UniquePokeCaptureAccess._UniquePokeCaptureAccess(mergeCondition(filterCondition))
	
	
	// OTHER	--------------------
	
	/**
	  * Updates the creation times of the targeted poke captures
	  * @param newCreated A new created to assign
	  * @return Whether any poke capture was affected
	  */
	def created_=(newCreated: Instant)(implicit connection: Connection) = 
		putColumn(model.createdColumn, newCreated)
	
	/**
	  * Updates the levels of the targeted poke captures
	  * @param newLevel A new level to assign
	  * @return Whether any poke capture was affected
	  */
	def level_=(newLevel: Int)(implicit connection: Connection) = putColumn(model.levelColumn, newLevel)
	
	/**
	  * Updates the poke ids of the targeted poke captures
	  * @param newPokeId A new poke id to assign
	  * @return Whether any poke capture was affected
	  */
	def pokeId_=(newPokeId: Int)(implicit connection: Connection) = putColumn(model.pokeIdColumn, newPokeId)
	
	/**
	  * Updates the run ids of the targeted poke captures
	  * @param newRunId A new run id to assign
	  * @return Whether any poke capture was affected
	  */
	def runId_=(newRunId: Int)(implicit connection: Connection) = putColumn(model.runIdColumn, newRunId)
}

