package pw.elka.mobiasystent.model

import android.provider.ContactsContract


/**
 * Zmieniam koncepcje na kompozycje bo jest bardziej elastyczna(Ale mniej wygodna)
 */
data class Guard(
    override var username: String = "",
    override var email: String


): User {
    lateinit var pList: List<User>
}