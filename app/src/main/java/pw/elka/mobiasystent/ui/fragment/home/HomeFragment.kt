package pw.elka.mobiasystent.ui.fragment.home


import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import pw.elka.mobiasystent.R
import pw.elka.mobiasystent.adapter.OccurenceAdapter
import pw.elka.mobiasystent.databinding.FragmentHomeBinding
import pw.elka.mobiasystent.model.Occurence
import pw.elka.mobiasystent.model.UserModel
import pw.elka.mobiasystent.viewmodels.OccurenceViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var viewModel: OccurenceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        Log.d("DUPA", "HOME fragment")

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        viewModel = ViewModelProviders.of(this).get(OccurenceViewModel::class.java)

        val adapter = OccurenceAdapter()
        binding.OccurenceRecyclerView.adapter = adapter

        binding.occurenceViewModel = viewModel

        binding.lifecycleOwner = this

        viewModel.allOccurences.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it.sortedByDescending { it.time }
            }}) // bind to repository



        binding.addOccurence.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_homeFragment_to_occurenceAdd)
        }

        viewModel.loggedUser.observe(this, Observer {
            value -> SetTitles(value)
        })

        return binding.root
    }

    private fun SetTitles(value: UserModel?){
        Log.d("dupa", "zmiana usera")
        binding.textTitle.text = createTitle(value)
        binding.textSubtitle.text = createSubTitle(value)
    }
    private fun createTitle(value: UserModel?): String{
        var outString = String()
        if(value == null)
            outString = "Witaj!"

        else
            outString = "Witaj " + value!!.firstName

        return outString
    }

    private fun createSubTitle(value: UserModel?): String{
        var outString = String()
        if(value == null)
            outString = "Oto wydarzenia"

        else{
            if(value!!.role == "PATIENT")
                outString =  "Oto Twoje wydarzenia."
            else
                outString =  "Oto wydarzenia Twojego pacjenta."
        }
        return outString
    }

}
