package pw.elka.mobiasystent.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import pw.elka.mobiasystent.model.FirestoreAPI
import pw.elka.mobiasystent.model.UserModel

class AssignedPersonViewModel: ViewModel() {
    var profilePictureURL = MutableLiveData<String>("")
    var name = MutableLiveData<String>("")
    var phoneNumber = MutableLiveData<String>("")
    var email = MutableLiveData<String>("")
    var address = MutableLiveData<String>("")

    private lateinit var myRef: DatabaseReference

    var assignedUser = UserModel()

    init{

        val currentUser = FirestoreAPI.getCurrentUser()

        var firestoreDB = FirebaseFirestore.getInstance()
        Log.d("DUPA", "2")
        firestoreDB.collection("users").document(currentUser.assignedPersonEmail).get()
            .addOnSuccessListener { document ->
                Log.d("DUPA", "3")
                if(document == null) {
                    Log.d("DUPA", "Brak takiego użytkownika")
                    Log.d("DUPA", "4a")
                }
                else{
                    Log.d("DUPA", "Pobrano dane użytkownika")


                    assignedUser = UserModel(
                        email = document["email"].toString(),
                        firstName = document["firstName"].toString(),
                        phoneNumber = document["phoneNumber"].toString()
                    )


                    name.value = assignedUser.firstName
                    email.value = assignedUser.email
                    phoneNumber.value = assignedUser.phoneNumber

                    Log.d("DUPA", "5")
                    //Log.d("DUPA", "Email: ${user.email}")
                    //Log.d("DUPA", "Name: ${user.firstName}")
                }


            }.addOnFailureListener {
                Log.d("DUPA", "Nieudane zapytanie")
                Log.d("DUPA", "6")
            }
        Log.d("DUPA", "7")



    }

    private fun getExampleUser(): UserModel
    {
        return UserModel(
            profilePictureURL = "@drawable/anonymity",
            firstName = "Zygmunt Pawłowicz",
            phoneNumber = "501788984",
            email = "ZygiPawlo@gmail.com",
            homeAddress = "Warszawa, ul. Swiętokrzyska 1"

        )
    }


}