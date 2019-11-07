package pw.elka.mobiasystent.modelcontroller

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DatabaseController {
    private lateinit var database: DatabaseReference
    constructor() {
        database = FirebaseDatabase.getInstance().reference

    }
}
