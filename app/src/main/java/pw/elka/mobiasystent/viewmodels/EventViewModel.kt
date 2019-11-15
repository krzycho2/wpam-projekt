package pw.elka.mobiasystent.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import pw.elka.mobiasystent.model.Event
import pw.elka.mobiasystent.repos.EventsRepository

class EventViewModel : ViewModel() {
    val TAG = "EVENT_VIEW_MODEL"
    var eventRepository = EventsRepository()
    var savedAddresses: MutableLiveData<List<Event>> = MutableLiveData()


    fun saveEventToFirebase(event: Event) {
        eventRepository.saveEvent(event).addOnFailureListener {
            Log.e(TAG, "Failed to save Event :(")
        }
    }

    fun getSavedEvents(): LiveData<List<Event>> {
        eventRepository.getSavedEvent()
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    savedAddresses.value = null
                    return@EventListener
                }

                var savedAddressList: MutableList<Event> = mutableListOf()
                for (doc in value!!) {
                    var addressItem = doc.toObject(Event::class.java)
                    savedAddressList.add(addressItem)
                }
                savedAddresses.value = savedAddressList
            })

        return savedAddresses
    }

    fun deleteEvent(event: Event) {
        eventRepository.deleteAddress(event).addOnFailureListener {
            Log.e(TAG, "Failed to delete Address")
        }
    }

}