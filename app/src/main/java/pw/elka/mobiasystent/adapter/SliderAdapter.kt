package pw.elka.mobiasystent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pw.elka.mobiasystent.databinding.SlideBinding
import pw.elka.mobiasystent.model.SlideContent

class SliderAdapter : RecyclerView.Adapter<OnBoardingViewHolder>() {
    var list: List<SlideContent> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        val view = SlideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnBoardingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setItems(newList: List<SlideContent>) {
        list = newList
        notifyDataSetChanged()
    }
}

class OnBoardingViewHolder(private val binding: SlideBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(slideContent: SlideContent) {
        binding.content = slideContent
    }
}