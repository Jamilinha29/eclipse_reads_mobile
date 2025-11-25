package com.example.projeto2

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.mili.eclipsereads.Central
import com.mili.eclipsereads.R

class Formulario_login : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_formulario_login, container, false)

        val emailEditText = view.findViewById<EditText>(R.id.Email00)
        val senhaEditText = view.findViewById<EditText>(R.id.senha00)
        val entrarButton = view.findViewById<Button>(R.id.button2)

        entrarButton.isEnabled = false

        val textWatcher = object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                val email = emailEditText.text.toString().trim()
                val senha = senhaEditText.text.toString().trim()
                entrarButton.isEnabled = email.isNotEmpty() && senha.isNotEmpty()
            }
        }

        emailEditText.addTextChangedListener(textWatcher)
        senhaEditText.addTextChangedListener(textWatcher)

        entrarButton.setOnClickListener {
            val intent = Intent(requireContext(), Central::class.java)
            startActivity(intent)
        }

        return view
    }
}
