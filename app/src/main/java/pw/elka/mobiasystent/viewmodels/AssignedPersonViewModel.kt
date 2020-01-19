package pw.elka.mobiasystent.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pw.elka.mobiasystent.model.UserModel

class AssignedPersonViewModel: ViewModel() {
    var  profilePictureURL = MutableLiveData<String>("")
    var name = MutableLiveData<String>("")
    var phoneNumber = MutableLiveData<String>("")
    var email = MutableLiveData<String>("")
    var address = MutableLiveData<String>("")


    private fun exampleUser(): UserModel
    {
        return UserModel(
            profilePictureURL = "@drawable/anonymity.png",
            firstName = "Zygmunt Pawłowicz",
            phoneNumber = "501 788 984",
            email = "ZygiPawlo@gmail.com"

        )
    }
}