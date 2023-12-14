package vf.poke.core.database.access.single.poke

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.UnconditionalView
import utopia.vault.sql.Condition
import vf.poke.core.database.factory.poke.PokeFactory
import vf.poke.core.database.model.poke.PokeModel
import vf.poke.core.model.stored.poke.Poke

/**
  * Used for accessing individual pokes
  * @author Mikko Hilpinen
  * @since 01.12.2023, v1.0
  */
object DbPoke extends SingleRowModelAccess[Poke] with UnconditionalView with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Factory used for constructing database the interaction models
	  */
	protected def model = PokeModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = PokeFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted poke
	  * @return An access point to that poke
	  */
	def apply(id: Int) = DbSinglePoke(id)
	
	/**
	  * @param condition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique pokes.
	  * @return An access point to the poke that satisfies the specified condition
	  */
	protected def filterDistinct(condition: Condition) = UniquePokeAccess(mergeCondition(condition))
}

