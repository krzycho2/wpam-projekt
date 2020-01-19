package pw.elka.mobiasystent.repos

import android.content.Intent
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import pw.elka.mobiasystent.model.AccountType
import pw.elka.mobiasystent.model.FirestoreAPI
import pw.elka.mobiasystent.model.Occurence
import pw.elka.mobiasystent.model.UserModel
import pw.elka.mobiasystent.utils.FirestoreUtil
import pw.elka.mobiasystent.utils.MyApplication

class OccurencesRepository {
    val TAG = "EVENT_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    lateinit var user: UserModel
    val occurencePath: String = "occurences"

// Jeśli user to guard, to otrzymuje Occurences od swojego pacjenta

    fun saveOccurence(occurence: Occurence): Task<Void> {

        user = FirestoreAPI.getCurrentUser()
        var emailToAdd: String = user!!.email
        if (user!!.role == AccountType.GUARD.toString()) {
            emailToAdd = MyApplication.currentUser!!.assignedPersonEmail

        }
        else {
            emailToAdd = user!!.email
        }

        var documentReference = firestoreDB.collection("users").document(emailToAdd)
            .collection(occurencePath).document(occurence.occurenceId)
        return documentReference.set(occurence)
    }

    fun getSavedOccurence(): CollectionReference {

        user = MyApplication.currentUser!!

        var emailToAdd: String = user!!.email
        if (user!!.role == AccountType.GUARD.toString()) {
            emailToAdd = MyApplication.currentUser!!.assignedPersonEmail
        } else {
            emailToAdd = user!!.email
        }
        var collectionReference =
            firestoreDB.collection("users/${emailToAdd}/" + occurencePath)

        Log.d("DUPA", "Pobrano eventy usera");
        return collectionReference
    }

    fun deleteOccurences(occurence: Occurence): Task<Void> {

        user = FirestoreAPI.getCurrentUser()
        var emailToAdd: String = user!!.email
        if (user!!.role == AccountType.GUARD.toString()) {
            emailToAdd = MyApplication.currentUser!!.assignedPersonEmail
        } else {
            emailToAdd = user!!.email
        }
        var documentReference =
            firestoreDB.collection("users/${emailToAdd}/" + occurencePath)
                .document(occurence.occurenceId)
        return documentReference.delete()
    }

}