package com.example.misandroid.ui.signals

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.misandroid.MainActivity.Companion.currentUser
import com.example.misandroid.R
import com.example.misandroid.database.UserSignalEntity
import com.example.misandroid.databinding.FragmentSignalsBinding
import com.example.misandroid.databinding.SignalDialogBinding

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

        val recyclerView: RecyclerView = binding.signalCard
        signalsViewModel.signalList.observe(viewLifecycleOwner) {
            recyclerView.adapter = SignalAdapter(it)
        }

        val addButton = binding.floatingActionButton

        addButton.setOnClickListener{
            val dialog = Dialog(requireContext())

            dialog.setContentView(R.layout.signal_dialog)

            dialog.setCancelable(true)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val editText1 = dialog.findViewById<EditText>(R.id.signal1)
            val editText2 = dialog.findViewById<EditText>(R.id.signal2)
            val editText3 = dialog.findViewById<EditText>(R.id.signal3)

            editText1.filters = arrayOf(InputFilter.LengthFilter(2))
            editText2.filters = arrayOf(InputFilter.LengthFilter(2))
            editText3.filters = arrayOf(InputFilter.LengthFilter(2))

            val dialogButton1 = dialog.findViewById<Button>(R.id.dialog_button1)
            val dialogButton2 = dialog.findViewById<Button>(R.id.dialog_button2)

            dialogButton1.setOnClickListener {
                if(editText1.text.isNotEmpty() && editText2.text.isNotEmpty() && editText3.text.isNotEmpty() ) {
                    signalsViewModel.insertSignal(
                        UserSignalEntity(
                            0, currentUser!!.macAddress,
                            "${editText1.text},${editText2.text},${editText3.text}",
                            "wiliboxas1,wiliboxas2,wiliboxas3"
                        )
                    )
                    dialog.dismiss()
                }
            }
            dialogButton2.setOnClickListener {
                dialog.dismiss()
            }

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}