package com.example.misandroid.ui.signals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.misandroid.databinding.FragmentSignalsBinding

class SignalsFragment: Fragment() {
    private var _binding: FragmentSignalsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val signalsViewModel =
            ViewModelProvider(this).get(SignalsViewModel::class.java)

        _binding = FragmentSignalsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.signalList
        signalsViewModel.signalList.observe(viewLifecycleOwner) {
            recyclerView.adapter = SignalAdapter(it)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}