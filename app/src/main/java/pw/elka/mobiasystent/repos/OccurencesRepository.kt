package pw.elka.mobiasystent.repos

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import pw.elka.mobiasystent.model.Occurence

class OccurencesRepository {
    val TAG = "EVENT_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var user = FirebaseAuth.getInstance().currentUser;
    val occurencePath: String = "occurences"


    fun saveOccurence(occurence: Occurence): Task<Void> {
        var documentReference = firestoreDB.collection("users").document(user!!.email.toString())
            .collection(occurencePath).document(occurence.occurenceId)
        return documentReference.set(occurence)
    }

    fun getSavedOccurence(): CollectionReference {
        var collectionReference =
            firestoreDB.collection("users/${user!!.email.toString()}/" + occurencePath)
        return collectionReference
    }

    fun deleteOccurences(occurence: Occurence): Task<Void> {
        var documentReference =
            firestoreDB.collection("users/${user!!.email.toString()}/" + occurencePath)
                .document(occurence.occurenceId)
        return documentReference.delete()
    }

}