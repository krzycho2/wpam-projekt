package pw.elka.mobiasystent.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import pw.elka.mobiasystent.utils.FirestoreUtil
import pw.elka.mobiasystent.utils.MyApplication


class FirestoreAPI {
    companion object{
        fun setAppUser()
        {
            var firestoreDB = FirebaseFirestore.getInstance()

            val ref = firestoreDB.collection("users").document(FirebaseAuth.getInstance().currentUser!!.email!!)

            ref.get().addOnSuccessListener {
                val userInfo = it.toObject(UserModel::class.java)

                MyApplication.currentUser = userInfo
                Log.d("DUPA", "dodano usera");
                MyApplication.currentUser!!.active = true
                FirestoreUtil.updateUser(MyApplication.currentUser!!) {
                }
            }.addOnFailureListener {
                Log.d("DUPA", "Błąd");
            }
        }
    }
}