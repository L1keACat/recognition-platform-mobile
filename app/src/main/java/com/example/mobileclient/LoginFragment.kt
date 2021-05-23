package com.example.mobileclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {

    private lateinit var loginButton: Button
    private lateinit var textInput: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton = view.findViewById(R.id.login_button)
        textInput = view.findViewById(R.id.input_edittext)

        loginButton.setOnClickListener{
            val username = textInput.text.toString()
            val action = LoginFragmentDirections.actionLoginFragmentToImagePickerFragment(username)
            findNavController().navigate(action)
            Toast.makeText(context, "Logged in as $username", Toast.LENGTH_SHORT).show()
        }
    }
}