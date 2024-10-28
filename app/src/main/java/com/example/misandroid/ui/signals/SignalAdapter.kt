package com.example.misandroid.ui.signals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.misandroid.R
import com.example.misandroid.database.UserSignalEntity

class SignalAdapter(
    private val dataset: List<UserSignalEntity>
) : RecyclerView.Adapter<SignalAdapter.SignalViewHolder>(){
    class SignalViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val strength: TextView = view.findViewById(R.id.signal_strengths)
        val names: TextView = view.findViewById(R.id.signal_names)
        val coordinated: TextView = view.findViewById(R.id.coordinates)
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
        holder.strength.text = item.strength
        holder.names.text = "wiliboxas"
    }
}