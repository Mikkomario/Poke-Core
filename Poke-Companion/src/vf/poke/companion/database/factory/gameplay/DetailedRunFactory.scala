package vf.poke.companion.database.factory.gameplay

import utopia.vault.model.immutable.{Row, Table}
import utopia.vault.nosql.factory.row.FromRowFactoryWithTimestamps
import utopia.vault.sql.JoinType
import vf.poke.companion.model.combined.gameplay.DetailedRun
import vf.poke.core.database.factory.game.GameFactory
import vf.poke.core.database.factory.randomization.RandomizationFactory
import vf.poke.core.database.model.game.GameModel

import scala.util.Try

/**
 * Factory used for reading detailed game run information
 * @author Mikko Hilpinen
 * @since 2.12.2023, v1.0
 */
object DetailedRunFactory extends FromRowFactoryWithTimestamps[DetailedRun]
{
	// IMPLEMENTED  --------------------
	
	override def table: Table = RunFactory.table
	override def joinedTables: Seq[Table] = RandomizationFactory.tables ++ GameFactory.tables
	override def joinType: JoinType = JoinType.Inner
	
	override def creationTimePropertyName: String = RunFactory.creationTimePropertyName
	
	override def apply(row: Row): Try[DetailedRun] = RunFactory(row)
		.flatMap { run => RandomizationFactory(row).map { randomization =>
			val gameName = row(GameModel.nameColumn).getString
			DetailedRun(run, randomization, gameName)
		} }
}
