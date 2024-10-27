package com.example.misandroid.ui.signal_map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.misandroid.R

class SignalMapAdapter (
    private val cells: List<MapCell>
) : RecyclerView.Adapter<SignalMapAdapter.CellViewHolder>() {

    inner class CellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.cell_title)

        fun bind(cell: MapCell) {
            textView.text = "${cell.x},${cell.y}"
            itemView.setBackgroundColor(
                if (cell.isMeasured) android.graphics.Color.YELLOW else android.graphics.Color.WHITE
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.map_cell, parent, false)
        return CellViewHolder(view)
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        holder.bind(cells[position])
    }

    override fun getItemCount(): Int = cells.size
}