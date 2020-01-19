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
import pw.elka.mobiasystent.R
import pw.elka.mobiasystent.databinding.FragmentCalendarBinding


class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_calendar,
            container,
            false)

//        binding.buttonAssignedPerson.setOnClickListener { view: View ->
//            view.findNavController().navigate(R.id.action_calendarFragment_to_assignedPersonFragment)
//        }
//
//        binding.buttonHome.setOnClickListener { view: View ->
//            view.findNavController().navigate(R.id.action_calendarFragment_to_homeFragment)
//        }



        return binding.root
    }


}
