package pw.elka.mobiasystent.ui.fragment.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pw.elka.mobiasystent.utils.MyApplication

class SettingsViewModel : ViewModel() {
    var phoneNumber: String
    var connectedEmail:String


    init{
        phoneNumber = MyApplication.currentUser!!.phoneNumber;
        connectedEmail = MyApplication.currentUser!!.assignedPersonEmail
    }
}
