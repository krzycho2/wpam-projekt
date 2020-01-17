package pw.elka.mobiasystent.ui.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.firebase.firestore.auth.User
import pw.elka.mobiasystent.R
import pw.elka.mobiasystent.databinding.FragmentAssignedPersonBinding
import pw.elka.mobiasystent.model.UserModel


class AssignedPersonFragment : Fragment() {

    private lateinit var binding: FragmentAssignedPersonBinding

    private lateinit var user: UserModel

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

        binding.buttonHome.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_assignedPersonFragment_to_homeFragment)
        }

        binding.buttonCalendar.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_assignedPersonFragment_to_calendarFragment)
        }


        return binding.root
    }

    private fun exampleUser(): UserModel
    {
        return UserModel()
    }

}
