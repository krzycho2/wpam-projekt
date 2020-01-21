package pw.elka.mobiasystent.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.internal.Mutable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.*
import pw.elka.mobiasystent.model.AccountType
import pw.elka.mobiasystent.model.Occurence
import pw.elka.mobiasystent.model.OccurenceType
import pw.elka.mobiasystent.model.UserModel
import pw.elka.mobiasystent.repos.OccurencesRepository
import pw.elka.mobiasystent.utils.FirestoreUtil
import pw.elka.mobiasystent.utils.MyApplication
import java.lang.Exception

class OccurenceViewModel : ViewModel() {
    val TAG = "OCCURENCE_VIEW_MODEL"
    var occurenceRepository = OccurencesRepository()

    // Coroutines
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var allOccurences = MutableLiveData<List<Occurence>>()


    var singleOccurence = MutableLiveData<Occurence>()

    var loggedUser = MutableLiveData<UserModel?>()

    var userRole = MutableLiveData<String?>()

    init {
        //var _allOccurences = mutableListOf<Occurence>()
        if (MyApplication.currentUser == null) {
            var firestoreDB = FirebaseFirestore.getInstance()

            val ref = firestoreDB.collection("users")
                .document(FirebaseAuth.getInstance().currentUser!!.email!!)
            ref.get().addOnSuccessListener {
                val userInfo = it.toObject(UserModel::class.java)
                MyApplication.currentUser = userInfo
                MyApplication.currentUser!!.active = true

                loggedUser.value = MyApplication.currentUser

                Log.d("dupa", "user: ${loggedUser} role: ${userRole.value}")

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
//                        _allOccurences = mutableListOf()
                        return@EventListener
                    }

                    var savedOccurencesList: MutableList<Occurence> = mutableListOf()
                    for (doc in value!!) {
                        var addressItem = doc.toObject(Occurence::class.java)
                        savedOccurencesList.add(addressItem)
                    }
//                    _allOccurences = savedOccurencesList
                        allOccurences.value = savedOccurencesList
//                    for(occurence in _allOccurences)
//                        Log.d("dupa", "event: ${occurence.description}")
                })
        }

    fun setUser(){

    }
}

    private fun initOccurences(){
        uiScope.launch {
            allOccurences.value = getOccurences()

}
    }

    private suspend fun getOccurences(): List<Occurence>{
        return withContext(Dispatchers.IO){
            getSavedOccurences()
        }
    }

    //fun getSavedOccurences(): LiveData<List<Occurence>>
    fun getSavedOccurences(): List<Occurence> {
        var _allOccurences = mutableListOf<Occurence>()
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
//                            allOccurences.value = null
                            _allOccurences = mutableListOf()
                            return@EventListener
                        }

                        var savedOccurencesList: MutableList<Occurence> = mutableListOf()
                        for (doc in value!!) {
                            var addressItem = doc.toObject(Occurence::class.java)
                            savedOccurencesList.add(addressItem)
                        }
                        _allOccurences = savedOccurencesList

                    })
            }
        } else {
            occurenceRepository.getSavedOccurence()
                .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
//                            allOccurences.value = null
                        _allOccurences = mutableListOf()
                        return@EventListener
                    }

                    var savedOccurencesList: MutableList<Occurence> = mutableListOf()
                    for (doc in value!!) {
                        var addressItem = doc.toObject(Occurence::class.java)
                        savedOccurencesList.add(addressItem)
                    }
                    _allOccurences = savedOccurencesList
//                        allOccurences.value = savedOccurencesList
                    for(occurence in _allOccurences)
                        Log.d("dupa", "event: ${occurence.description}")
                })
        }
        return _allOccurences
    }

    fun saveOccurenceToFirebase(occurence: Occurence) {
        occurenceRepository.saveOccurence(occurence).addOnFailureListener {
            Log.e(TAG, "Failed to save Occurence :(")
        }
    }

    fun deleteOccurence(occurence: Occurence) {
        occurenceRepository.deleteOccurences(occurence).addOnFailureListener {
            Log.e(TAG, "Failed to delete Occurences")
        }
    }
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

    private fun sortOccurencesByTime(occurences: List<Occurence>){

    }

    var savedOcurrencesTest = MutableLiveData<List<Occurence>>(exampleOccurences)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}