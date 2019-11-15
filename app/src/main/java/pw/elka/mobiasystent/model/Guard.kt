package pw.elka.mobiasystent.model


/**
 * Zmieniam koncepcje na kompozycje bo jest bardziej elastyczna(Ale mniej wygodna)
 */
data class Guard(
    var pList: List<User>,
    val base: User
)