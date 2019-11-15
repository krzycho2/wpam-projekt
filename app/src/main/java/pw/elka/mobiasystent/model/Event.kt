package pw.elka.mobiasystent.model

import java.io.Serializable
import java.time.LocalDateTime

class Event (
    var eventId: String = "",
    var description: String = "",
    var time: LocalDateTime? = null
) : Serializable {

}