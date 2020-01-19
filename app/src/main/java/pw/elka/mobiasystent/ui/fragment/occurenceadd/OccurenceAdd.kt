package pw.elka.mobiasystent.ui.fragment.occurenceadd

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.navigation.findNavController
import pw.elka.mobiasystent.R

import pw.elka.mobiasystent.databinding.OccurenceAddFragmentBinding
import pw.elka.mobiasystent.model.AccountType
import pw.elka.mobiasystent.model.Occurence
import pw.elka.mobiasystent.model.OccurenceType
import pw.elka.mobiasystent.repos.OccurencesRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class OccurenceAdd : Fragment() {

    companion object {
        fun newInstance() =
            OccurenceAdd()
    }

    private lateinit var viewModel: OccurenceAddViewModel
    private lateinit var binding: OccurenceAddFragmentBinding
    private var year: Int = LocalDateTime.now().year
    private var day: Int = LocalDateTime.now().dayOfMonth
    private var month: Int = LocalDateTime.now().monthValue
    private var hour: Int = LocalDateTime.now().hour
    private var minutes: Int = LocalDateTime.now().minute
    private val occurenceRepository = OccurencesRepository()
    private var type = OccurenceType.ALERT

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OccurenceAddFragmentBinding.inflate(inflater, container, false)
        binding.dateText.setText(LocalDate.of(this.year, this.month, this.day).toString())
        binding.timeText.setText(LocalTime.of(this.hour, this.minutes).toString())
        binding.pickDate.setOnClickListener { view: View ->
            DatePickerDialog(view.context, { view2, year, month, dayOfMonth ->
                this.day = dayOfMonth;
                this.month = month;
                this.year = year
                binding.dateText.setText(LocalDate.of(this.year, this.month, this.day).toString())

            }, year, month, day).show()
        }

        binding.pickTime.setOnClickListener { view: View ->
            TimePickerDialog(view.context, { view2, hourofDay, minute ->
                this.hour = hourofDay
                this.minutes = minute
                binding.timeText.setText(LocalTime.of(this.hour, this.minutes).toString())
            }, hour, minutes, true).show()
        }

        binding.confirm.setOnClickListener { view: View ->
            val time =
                LocalDateTime.of(year, month, day, hour, minutes).atZone(ZoneId.systemDefault())
                    .toInstant().toEpochMilli()
            val description = binding.description.text.toString()
            val occurence = Occurence(
                System.currentTimeMillis().toString(),
                description,
                false,
                type,
                time
            )
            this.occurenceRepository.saveOccurence(occurence)

            view.findNavController().navigate(R.id.action_occurenceAdd_to_homeFragment)

        }

        binding.radio.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            val choosen = view?.findViewById<RadioButton>(i)
            if (choosen?.text == "Alarm") {
                this.type = OccurenceType.ALERT
            } else if (choosen?.text == "Wizyta") {
                this.type = OccurenceType.VISIT
            } else if (choosen?.text == "Inne") {
                this.type = OccurenceType.OTHER
            } else {
                this.type = OccurenceType.MEDICINETAKE
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OccurenceAddViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
