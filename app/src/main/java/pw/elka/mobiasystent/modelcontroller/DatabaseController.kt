package pw.elka.mobiasystent.modelcontroller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import pw.elka.mobiasystent.model.Event

class DatabaseController : ViewModel() {

    private val events: MutableLiveData<Event> by lazy {
        MutableLiveData<Event>()
    }




//
//

//
//    private lateinit var database: DatabaseReference
//    constructor() {
//        database = FirebaseDatabase.getInstance().reference
//    }
}
