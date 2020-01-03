package pw.elka.mobiasystent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pw.elka.mobiasystent.R
import pw.elka.mobiasystent.model.Occurence
import pw.elka.mobiasystent.utils.TextItemViewHolder

class OccurenceAdapter: RecyclerView.Adapter<TextItemViewHolder>() {

    var data = listOf<Occurence>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.description
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.text_item_view, parent, false) as TextView

        return TextItemViewHolder(view)
    }


}