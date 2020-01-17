package pw.elka.mobiasystent.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import pw.elka.mobiasystent.model.Occurence
import pw.elka.mobiasystent.model.OccurenceType
import pw.elka.mobiasystent.model.UserModel
import pw.elka.mobiasystent.repos.OccurencesRepository
import pw.elka.mobiasystent.utils.FirestoreUtil
import pw.elka.mobiasystent.utils.MyApplication

class OccurenceViewModel : ViewModel() {
    val TAG = "OCCURENCE_VIEW_MODEL"
    var occurenceRepository = OccurencesRepository()

    // Property with getter
    private var allOccurences: MutableLiveData<List<Occurence>> = MutableLiveData()

    var singleOccurence: MutableLiveData<String> = MutableLiveData("Adam Kowalski poszedł do kina.")

    var exampleOccurences: MutableList<Occurence> = mutableListOf(
        Occurence(
            occurenceId = "1",
            type = OccurenceType.VISIT,
            description = "Adam Kowalski: wizyta u lekarza"
        ),
        Occurence(
            occurenceId = "2",
            type = OccurenceType.ALERT,
            description = "Adam Kowalski: Upadek"
        ),
        Occurence(
            occurenceId = "3",
            type = OccurenceType.MEDICINETAKE,
            description = "Adam Kowalski: Pobranie leku"
        )
    )

    var savedOcurrencesTest = MutableLiveData<List<Occurence>>(exampleOccurences)

    fun saveOccurenceToFirebase(occurence: Occurence) {
        occurenceRepository.saveOccurence(occurence).addOnFailureListener {
            Log.e(TAG, "Failed to save Occurence :(")
        }
    }

    fun getSavedOccurences(): LiveData<List<Occurence>> {
        if (MyApplication.currentUser == null) {
            var firestoreDB = FirebaseFirestore.getInstance()

            val ref = firestoreDB.collection("users")
                .document(FirebaseAuth.getInstance().currentUser!!.email!!)
            ref.get().addOnSuccessListener {
                val userInfo = it.toObject(UserModel::class.java)
                MyApplication.currentUser = userInfo
                MyApplication.currentUser!!.active = true
                FirestoreUtil.updateUser(MyApplication.currentUser!!) {
                }

                occurenceRepository.getSavedOccurence()
                    .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e)
                            allOccurences.value = null
                            return@EventListener
                        }

                        var savedOccurencesList: MutableList<Occurence> = mutableListOf()
                        for (doc in value!!) {
                            var addressItem = doc.toObject(Occurence::class.java)
                            savedOccurencesList.add(addressItem)
                        }
                        allOccurences.value = savedOccurencesList
                    })
            }
        } else {
            occurenceRepository.getSavedOccurence()
                .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                        allOccurences.value = null
                        return@EventListener
                    }

                    var savedOccurencesList: MutableList<Occurence> = mutableListOf()
                    for (doc in value!!) {
                        var addressItem = doc.toObject(Occurence::class.java)
                        savedOccurencesList.add(addressItem)
                    }
                    allOccurences.value = savedOccurencesList
                })
        }
        return allOccurences
    }

    fun deleteOccurence(occurence: Occurence) {
        occurenceRepository.deleteOccurences(occurence).addOnFailureListener {
            Log.e(TAG, "Failed to delete Occurences")
        }
    }


}