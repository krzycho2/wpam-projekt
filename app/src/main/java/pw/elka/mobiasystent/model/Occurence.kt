package pw.elka.mobiasystent.model

import java.io.Serializable
import java.time.LocalDateTime

class Occurence(
    var occurenceId: String = "",
    var description: String = "",
    var checked: Boolean = false,
    var type:OccurenceType = OccurenceType.ALERT,
    var time: Long = 0
) : Serializable