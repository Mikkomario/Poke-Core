package vf.poke.core.util

import utopia.flow.async.context.ThreadPool
import utopia.flow.util.logging.{Logger, SysErrLogger}
import utopia.vault.database.ConnectionPool

import scala.concurrent.ExecutionContext

/**
 * Contains some commonly used values
 * @author Mikko Hilpinen
 * @since 1.12.2023, v1.0
 */
object Common
{
	implicit val log: Logger = SysErrLogger
	implicit val exc: ExecutionContext = new ThreadPool("Poke")
	implicit val cPool: ConnectionPool = new ConnectionPool()
}
