package vf.poke.companion.database.access.single.gameplay.poke_capture

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.poke.companion.model.stored.gameplay.PokeCapture

/**
  * An access point to individual poke captures, based on their id
  * @author Mikko Hilpinen
  * @since 02.12.2023, v1.0
  */
case class DbSinglePokeCapture(id: Int) 
	extends UniquePokeCaptureAccess with SingleIntIdModelAccess[PokeCapture]

