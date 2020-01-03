package pw.elka.mobiasystent.ui.fragment.home


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
import pw.elka.mobiasystent.R
import pw.elka.mobiasystent.databinding.FragmentHomeBinding
import pw.elka.mobiasystent.model.Occurence
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
        //binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false)

        viewModel = ViewModelProviders.of(this).get(OccurenceViewModel::class.java)
        binding.occurenceViewModel = viewModel

        binding.lifecycleOwner = this

//        viewModel.savedOccurences.observe(this, Observer{textOfOccurence -> binding.TextOccurence.text = textOfOccurence})
        viewModel.singleOccurence.observe(this, Observer{occurence -> binding.TextOccurence.text = occurence})
        return binding.root
    }
}
