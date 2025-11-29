package com.mili.eclipsereads.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.mili.eclipsereads.ui.home.Central
import com.mili.eclipsereads.R

class Formulario_login : Fragment() {

    private val PREFS_NAME = "com.mili.eclipsereads.prefs"
    private val PREF_EMAIL = "email"
    private val PREF_PASSWORD = "password"
    private val PREF_REMEMBER = "remember"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_formulario_login, container, false)

        val emailEditText = view.findViewById<EditText>(R.id.Email00)
        val senhaEditText = view.findViewById<EditText>(R.id.senha00)
        val entrarButton = view.findViewById<Button>(R.id.button2)
        val rememberMeCheckBox = view.findViewById<CheckBox>(R.id.checkBox)

        val sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val shouldRemember = sharedPreferences.getBoolean(PREF_REMEMBER, false)

        if (shouldRemember) {
            emailEditText.setText(sharedPreferences.getString(PREF_EMAIL, ""))
            senhaEditText.setText(sharedPreferences.getString(PREF_PASSWORD, ""))
            rememberMeCheckBox.isChecked = true
        }

        entrarButton.isEnabled = emailEditText.text.isNotEmpty() && senhaEditText.text.isNotEmpty()

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val email = emailEditText.text.toString().trim()
                val senha = senhaEditText.text.toString().trim()
                entrarButton.isEnabled = email.isNotEmpty() && senha.isNotEmpty()
            }
        }

        emailEditText.addTextChangedListener(textWatcher)
        senhaEditText.addTextChangedListener(textWatcher)

        entrarButton.setOnClickListener {
            val editor = sharedPreferences.edit()
            if (rememberMeCheckBox.isChecked) {
                editor.putString(PREF_EMAIL, emailEditText.text.toString())
                editor.putString(PREF_PASSWORD, senhaEditText.text.toString())
                editor.putBoolean(PREF_REMEMBER, true)
            } else {
                editor.remove(PREF_EMAIL)
                editor.remove(PREF_PASSWORD)
                editor.putBoolean(PREF_REMEMBER, false)
            }
            editor.apply()

            val intent = Intent(requireContext(), Central::class.java)
            startActivity(intent)
        }

        return view
    }
}