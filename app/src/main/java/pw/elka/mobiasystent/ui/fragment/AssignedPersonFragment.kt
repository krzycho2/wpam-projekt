package pw.elka.mobiasystent.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_assigned_person.*
import pw.elka.mobiasystent.R
import pw.elka.mobiasystent.databinding.FragmentAssignedPersonBinding
import pw.elka.mobiasystent.model.UserModel
import pw.elka.mobiasystent.viewmodels.AssignedPersonViewModel


class AssignedPersonFragment : Fragment() {

    private lateinit var binding: FragmentAssignedPersonBinding

    private lateinit var user: UserModel

    private lateinit var viewModel: AssignedPersonViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_assigned_person,
            container,
            false)

//        binding.buttonHome.setOnClickListener { view: View ->
//            view.findNavController().navigate(R.id.action_assignedPersonFragment_to_homeFragment)
//        }
//
//        binding.buttonCalendar.setOnClickListener { view: View ->
//            view.findNavController().navigate(R.id.action_assignedPersonFragment_to_calendarFragment)
//        }

        viewModel = ViewModelProviders.of(this).get(AssignedPersonViewModel::class.java)

        binding.buttonCall.setOnClickListener{view:View ->
            callToPerson()
            //Log.d("DUPA", "kliknieto guzik");
        }

        return binding.root
    }


    @SuppressLint("MissingPermission")
    fun callToPerson() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + viewModel.phoneNumber)
        context!!.startActivity(intent)
    }



}
