package com.example.misandroid.ui.signal_map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.misandroid.databinding.FragmentSignalMapBinding
import com.example.misandroid.ui.signals.SignalAdapter

class SignalMapFragment : Fragment() {

    private var _binding: FragmentSignalMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val signalMapViewModel =
            ViewModelProvider(this).get(SignalMapViewModel::class.java)

        _binding = FragmentSignalMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.signalMap

        signalMapViewModel.measurementList.observe(viewLifecycleOwner) { it ->
            val yMin = it.minOf { y->y.coordinates.split(',')[1].toInt() }
            val yMax = it.maxOf { y->y.coordinates.split(',')[1].toInt() }
            val xMin = it.minOf { x->x.coordinates.split(',')[0].toInt() }
            val xMax = it.maxOf { x->x.coordinates.split(',')[0].toInt() }
            val rows = yMax - yMin + 1
            val columns = xMax - xMin + 1
            recyclerView.layoutManager = GridLayoutManager(this.context, columns)
            val cells = List(rows * columns) { index ->
                val x = index % columns
                val y = index / columns
                MapCell(x + xMin, yMax-y,
                    isMeasured = it.any { e -> e.coordinates == "${x+xMin},${yMax-y}" },
                    isSelected = signalMapViewModel.isMeasured(it.firstOrNull{i -> i.coordinates == "${x+xMin},${yMax-y}"}?.measurementId))
            }
            recyclerView.adapter = SignalMapAdapter(cells)
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}