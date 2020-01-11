package pw.elka.mobiasystent.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import pw.elka.mobiasystent.model.UserModel

object FirestoreUtil {
    val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document(
            "users/${FirebaseAuth.getInstance().currentUser?.email
                ?: throw NullPointerException("UID is null.")}"
        )

    fun updateUser(userModel: UserModel, onComplete: (String) -> Unit) {

        val task = currentUserDocRef.set(userModel)

        task.continueWith {
            if (it.isSuccessful) {
                onComplete("success")
            }
        }.addOnFailureListener {
            onComplete("failure")
        }
    }
}