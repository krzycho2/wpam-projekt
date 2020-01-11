package pw.elka.mobiasystent.model

interface User {
    var userID: String
    var role: UserRole
    var username: String
    var email: String
    var telNumber: String

}