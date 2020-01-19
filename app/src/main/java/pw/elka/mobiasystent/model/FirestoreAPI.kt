package pw.elka.mobiasystent.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import pw.elka.mobiasystent.utils.FirestoreUtil
import pw.elka.mobiasystent.utils.MyApplication


class FirestoreAPI {
    companion object{

        fun getCurrentUser() : UserModel
        {
            if(MyApplication.currentUser == null)
                setAppUser()

            return MyApplication.currentUser!!
        }

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

        fun updateAppUser(user:UserModel)
        {
            var firestoreDB = FirebaseFirestore.getInstance()
            val items = HashMap<String, Any>()
            items["email"] = FirebaseAuth.getInstance().currentUser!!.email!!
            items["firstName"] = user.firstName
            //items["lastName"] = ""
            //items["userName"] = ""
            items["phoneNumber"] = user.phoneNumber
            items["userID"] = user.userID
            items["profilePictureURL"] = user.profilePictureURL
            items["active"] = true
            items["assignedPersonEmail"] = user.assignedPersonEmail
            //
             items["role"] = user.role;
            Log.d("DUPA", "user.email!!")
            Log.d("DUPA", user.toString()
            )

            firestoreDB.collection("users").document(FirebaseAuth.getInstance().currentUser!!.email!!).set(items)
                .addOnSuccessListener {

                    Log.d("DUPA", "save user:success")

                }.addOnFailureListener {
                    Log.d("DUPA", "save user:error")
                }
        }


    }
}