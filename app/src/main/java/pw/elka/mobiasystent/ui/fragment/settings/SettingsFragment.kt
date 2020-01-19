package pw.elka.mobiasystent.ui.fragment.settings

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.fragment_settings.*

import pw.elka.mobiasystent.R
import pw.elka.mobiasystent.databinding.FragmentSettingsBinding
import pw.elka.mobiasystent.model.FirestoreAPI
import pw.elka.mobiasystent.utils.MyApplication

class SettingsFragment : Fragment() {
    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
       // viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
//        binding.lifecycleOwner = this
        val user = FirestoreAPI.getCurrentUser();
        binding.connectedEmail.setText(user.assignedPersonEmail)
        binding.phoneNumber.setText(user.phoneNumber)
        binding.confirm.setOnClickListener{v:View->
            user.phoneNumber = binding.phoneNumber.text.toString()
            user.assignedPersonEmail = binding.connectedEmail.text.toString()
            FirestoreAPI.updateAppUser(user)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
