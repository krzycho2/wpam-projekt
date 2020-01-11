package pw.elka.mobiasystent.model

data class Patient (
    override var username: String = "",
    override var email: String = "",
    override var userID: String = "",
    override var role: UserRole = UserRole.PATIENT,
    override var telNumber: String = ""
) : User {
    lateinit var gList: List<User>
}