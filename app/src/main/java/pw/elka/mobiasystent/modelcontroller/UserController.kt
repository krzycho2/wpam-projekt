package pw.elka.mobiasystent.modelcontroller

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import pw.elka.mobiasystent.model.AccountType
import pw.elka.mobiasystent.model.User

class UserController {
    var userMail= FirebaseAuth.getInstance().currentUser
    var firestoreDB = FirebaseFirestore.getInstance()

    fun initDB(type:AccountType, user:User) {

    }
}