package pw.elka.mobiasystent.repos

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PatientRepository {

    val TAG = "PATIENT_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var user = FirebaseAuth.getInstance().currentUser;
    val occurencePath: String = "occurences"
}