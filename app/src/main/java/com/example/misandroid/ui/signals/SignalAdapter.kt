package com.example.misandroid.ui.signals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.misandroid.R
import com.example.misandroid.database.StrengthEntity

class SignalAdapter(
    private val dataset: List<StrengthEntity>
) : RecyclerView.Adapter<SignalAdapter.SignalViewHolder>(){
    class SignalViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById(R.id.item_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignalViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return SignalViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: SignalViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.strengthId.toString()
    }
}