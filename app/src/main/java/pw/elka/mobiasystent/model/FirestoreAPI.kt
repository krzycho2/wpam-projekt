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
             items["role"] = user.role.toString();
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

        fun getAssignedUser(): UserModel{

            val currentUser = getCurrentUser()
            Log.d("DUPA", "Użytkownik: ${currentUser.email}")
            return getUserByEmail(currentUser.assignedPersonEmail)
        }

        fun getUserByEmail(email: String): UserModel{
            Log.d("DUPA", "0")
            var user = UserModel()
            Log.d("DUPA", "1")
            var firestoreDB = FirebaseFirestore.getInstance()
            Log.d("DUPA", "2")
            firestoreDB.collection("users").document(email).get()
                .addOnSuccessListener { document ->
                    Log.d("DUPA", "3")
                    if(document == null) {
                        Log.d("DUPA", "Brak takiego użytkownika")
                        Log.d("DUPA", "4a")
                    }
                    else{
                        Log.d("DUPA", "4b")
                        Log.d("DUPA", "Pobrano dane użytkownika")
                        //Log.d("DUPA", "Email: ${document["email"]}")
                        //Log.d("DUPA", "Name: ${document["firstName"]}")

                        user = UserModel(
                            email = document["email"].toString(),
                            firstName = document["firstName"].toString(),
                            phoneNumber = document["phoneNumber"].toString()
                        )

                        Log.d("DUPA", "5")
                        //Log.d("DUPA", "Email: ${user.email}")
                        //Log.d("DUPA", "Name: ${user.firstName}")
                    }


                }.addOnFailureListener {
                    Log.d("DUPA", "Nieudane zapytanie")
                    Log.d("DUPA", "6")
                }
            Log.d("DUPA", "7")
            Log.d("DUPA", "Email: ${user.email}")
            Log.d("DUPA", "Name: ${user.firstName}")
            Log.d("DUPA", "8")
            return user
        }

    }
}