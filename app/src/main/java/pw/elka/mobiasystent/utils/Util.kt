package pw.elka.mobiasystent.utils

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.occurence_item_view.view.*
import pw.elka.mobiasystent.R

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

class OccurenceViewHolder(val linearLayout: LinearLayout): RecyclerView.ViewHolder(linearLayout){

    var icon = linearLayout.findViewById<ImageView>(R.id.imageView)
    var date = linearLayout.findViewById<TextView>(R.id.date)
    var description = linearLayout.findViewById<TextView>(R.id.description)

}