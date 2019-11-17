package pw.elka.mobiasystent.model

import java.io.Serializable
import java.time.LocalDateTime

class Occurence (
    var occurenceId: String = "",
    var description: String = "",
    var time: LocalDateTime? = null
) : Serializable {

}