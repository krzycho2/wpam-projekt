package pw.elka.mobiasystent.model

enum class OccurenceType(val typeName: String) {
    ALERT("ALERT"),
    VISIT("VISIT"),
    MEDICINETAKE("MEDICINETAKE"),
    OTHER("OTHER")
}