package pw.elka.mobiasystent.ui.fragment.occurenceadd

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import pw.elka.mobiasystent.R

class OccurenceAdd : Fragment() {

    companion object {
        fun newInstance() =
            OccurenceAdd()
    }

    private lateinit var viewModel: OccurenceAddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.occurence_add_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OccurenceAddViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
