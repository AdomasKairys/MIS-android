package com.example.misandroid.ui.identification

import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.misandroid.MainActivity.Companion.USER_KEY
import com.example.misandroid.MainActivity.Companion.sharedPreferences
import com.example.misandroid.R
import com.example.misandroid.database.UserEntity
import com.example.misandroid.databinding.FragmentIdentificationBinding

class IdentificationFragment : Fragment() {
    private var _binding: FragmentIdentificationBinding? = null
    private var _navc: NavController? = null
    private var _identificationViewModel: IdentificationViewModel? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val navc get() = _navc!!
    private val identificationViewModel get() = _identificationViewModel!!


    private lateinit var userList : List<UserEntity>
    fun macFormat(){
        val etMacAddress = binding.etMacAddress

        // Input filter to allow only hexadecimal characters and colons
        val macAddressFilter = InputFilter { source, _, _, _, _, _ ->
            if (source.matches(Regex("[0-9A-Fa-f:]*"))) {
                source
            } else {
                ""
            }
        }
        etMacAddress.filters = arrayOf(macAddressFilter, InputFilter.LengthFilter(17))

        // Format the input as MAC address (XX:XX:XX:XX:XX:XX)
        etMacAddress.addTextChangedListener(object : TextWatcher {
            private var current = ""
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val formatted = s.toString().replace(":", "").chunked(2)
                    .joinToString(":")
                    .take(17)  // Max length for MAC address

                if (formatted != current) {
                    current = formatted
                    etMacAddress.setText(formatted.uppercase())
                    etMacAddress.setSelection(formatted.length)
                }
            }
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _identificationViewModel =
            ViewModelProvider(this).get(IdentificationViewModel::class.java)

        _binding = FragmentIdentificationBinding.inflate(inflater, container, false)
        binding.errorIdentification.visibility = INVISIBLE

        identificationViewModel.usersList.observe(viewLifecycleOwner){
            userList=it
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _navc = Navigation.findNavController(view)

        macFormat()

        val buttonLogin = binding.loginButton
        val buttonRegister = binding.registerButton


        buttonLogin.setOnClickListener {
            val etMacAddress = binding.etMacAddress
            if(etMacAddress.text.length == 17 && userList.any { us -> us.macAddress == etMacAddress.text.toString()}) {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.clear();
                editor.putString(USER_KEY, etMacAddress.text.toString())
                editor.commit();
                navc.navigate(R.id.navigation_home)
            }else{
                binding.errorIdentification.visibility = VISIBLE
                binding.errorIdentification.text = "some error"
                etMacAddress.text.clear()
            } }

        buttonRegister.setOnClickListener {
            val etMacAddress = binding.etMacAddress
            if(etMacAddress.text.length == 17 && !userList.any { us -> us.macAddress == etMacAddress.text.toString()}) {
                identificationViewModel.inserUser(UserEntity(etMacAddress.text.toString()))
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.clear();
                editor.putString(USER_KEY, etMacAddress.text.toString())
                editor.commit();
                navc.navigate(R.id.navigation_home)
            }else{
                binding.errorIdentification.visibility = VISIBLE
                binding.errorIdentification.text = "some error"
                etMacAddress.text.clear()
            } }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}