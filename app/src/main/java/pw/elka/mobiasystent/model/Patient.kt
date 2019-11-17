package pw.elka.mobiasystent.model

data class Patient (
    override var username: String = "",
    override var email: String
) : User {
    lateinit var gList: List<User>
}