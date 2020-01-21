package pw.elka.mobiasystent.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pw.elka.mobiasystent.R
import pw.elka.mobiasystent.model.Occurence
import pw.elka.mobiasystent.model.OccurenceType
import pw.elka.mobiasystent.utils.OccurenceViewHolder
import pw.elka.mobiasystent.utils.TextItemViewHolder
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class OccurenceAdapter: RecyclerView.Adapter<OccurenceViewHolder>(){
//class OccurenceAdapter: RecyclerView.Adapter<TextItemViewHolder>() {

    var data = listOf<Occurence>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: OccurenceViewHolder, position: Int) {
        val item = data[position]
        holder.date.text = createDateString(item.time)


        // icon
        val ocurType = item.type
        var imageID: Int
        when(ocurType){
            OccurenceType.ALERT -> imageID = R.drawable.caution
            OccurenceType.MEDICINETAKE -> imageID = R.drawable.drug
            OccurenceType.VISIT -> imageID = R.drawable.doctor
            else -> imageID = R.drawable.question
        }
        holder.icon.setImageResource(imageID)

        // description
        holder.description.text = item.description

        // if past occurence -> fade layout
        if(item.time < System.currentTimeMillis())
            holder.linearLayout.alpha = 0.5F

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OccurenceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.occurence_item_view, parent, false) as LinearLayout

        return OccurenceViewHolder(view)
    }

    private fun createDateString(timeInMili: Long): String?{
        try{
            val dateFormat = SimpleDateFormat("dd/MM/yyyy H:mm")
            val netDate = Date(timeInMili)
            return dateFormat.format(netDate)
        } catch(e: Exception){
            return e.toString()
        }
    }



}