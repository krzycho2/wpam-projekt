package pw.elka.mobiasystent.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import pw.elka.mobiasystent.model.Occurence
import pw.elka.mobiasystent.repos.OccurencesRepository

class OccurenceViewModel : ViewModel() {
    val TAG = "OCCURENCE_VIEW_MODEL"
    var occurenceRepository = OccurencesRepository()
    var savedOccurences: MutableLiveData<List<Occurence>> = MutableLiveData()


    fun saveOccurenceToFirebase(occurence: Occurence) {
        occurenceRepository.saveOccurence(occurence).addOnFailureListener {
            Log.e(TAG, "Failed to save Occurence :(")
        }
    }

    fun getSavedOccurences(): LiveData<List<Occurence>> {
        occurenceRepository.getSavedOccurence()
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    savedOccurences.value = null
                    return@EventListener
                }

                var savedOccurencesList: MutableList<Occurence> = mutableListOf()
                for (doc in value!!) {
                    var addressItem = doc.toObject(Occurence::class.java)
                    savedOccurencesList.add(addressItem)
                }
                savedOccurences.value = savedOccurencesList
            })

        return savedOccurences
    }

    fun deleteOccurence(occurence: Occurence) {
        occurenceRepository.deleteOccurences(occurence).addOnFailureListener {
            Log.e(TAG, "Failed to delete Occurences")
        }
    }

}