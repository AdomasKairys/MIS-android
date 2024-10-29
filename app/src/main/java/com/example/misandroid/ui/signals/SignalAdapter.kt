package com.example.misandroid.ui.signals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.misandroid.MainActivity.Companion.database
import com.example.misandroid.R
import com.example.misandroid.database.UserSignalEntity

class SignalAdapter(
    private val dataset: List<UserSignalEntity>,
    private val onClick: (input:Int) -> Unit
) : RecyclerView.Adapter<SignalAdapter.SignalViewHolder>(){
    class SignalViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val strength: TextView = view.findViewById(R.id.signal_strengths)
        val names: TextView = view.findViewById(R.id.signal_names)
        val coordinates: TextView = view.findViewById(R.id.coordinates)
        val card: CardView = view.findViewById(R.id.card_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignalViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.signal_card, parent, false)
        return SignalViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: SignalViewHolder, position: Int) {
        val item = dataset[position]
        Thread{
            val coords = database.measurementDao.getMeasurementsById(item.measurementId)!!.coordinates
            holder.coordinates.text = coords
        }.start()
        holder.strength.text = item.strength
        holder.names.text = "wiliboxas"
        holder.card.setOnClickListener {onClick(item.id)}
    }
}