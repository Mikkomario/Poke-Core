package vf.poke.core.database.access.single.poke.stat

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.PokeStatFactory
import vf.poke.core.database.model.poke.PokeStatModel
import vf.poke.core.model.stored.poke.PokeStat

/**
  * Used for accessing individual poke stats
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbPokeStat extends SingleRowModelAccess[PokeStat] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = PokeStatModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PokeStatFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted poke stat
	  * @return An access point to that poke stat
	  */
	def apply(id: Int) = DbSinglePokeStat(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique poke stats.
	  * @return An access point to the poke stat that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniquePokeStatAccess(mergeCondition(condition))
}

