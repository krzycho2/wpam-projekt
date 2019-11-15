package pw.elka.mobiasystent.repos

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import pw.elka.mobiasystent.model.Event

class EventsRepository {
    val TAG = "EVENT_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var user = FirebaseAuth.getInstance().currentUser;
    val eventPath: String = "events"


    fun saveEvent(event: Event): Task<Void> {
        var documentReference = firestoreDB.collection("users").document(user!!.email.toString())
            .collection(eventPath).document(event.eventId)
        return documentReference.set(event)
    }

    fun getSavedEvent(): CollectionReference {
        var collectionReference =
            firestoreDB.collection("users/${user!!.email.toString()}/" + eventPath)
        return collectionReference
    }

    fun deleteAddress(event: Event): Task<Void> {
        var documentReference =
            firestoreDB.collection("users/${user!!.email.toString()}/" + eventPath)
                .document(event.eventId)
        return documentReference.delete()
    }

}