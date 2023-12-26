package vf.poke.core.util

import utopia.flow.async.context.ThreadPool
import utopia.flow.util.logging.{Logger, SysErrLogger}
import utopia.flow.util.console.ConsoleExtensions._
import utopia.vault.database.{Connection, ConnectionPool}

import scala.concurrent.ExecutionContext
import scala.io.StdIn

/**
 * Contains some commonly used values
 * @author Mikko Hilpinen
 * @since 1.12.2023, v1.0
 */
object Common
{
	// IMPLICIT --------------------------
	
	implicit val log: Logger = SysErrLogger
	implicit val exc: ExecutionContext = new ThreadPool("Poke")
	implicit val cPool: ConnectionPool = new ConnectionPool()
	
	
	// OTHER    --------------------------
	
	def setupDb() = {
		val user = StdIn.readNonEmptyLine("Please specify DB user to use (default = root)").getOrElse("root")
		val password = StdIn.readLine("Please specify the password to use when connecting to the DB")
		Connection.modifySettings { _.copy(user = user, password = password) }
	}
}
