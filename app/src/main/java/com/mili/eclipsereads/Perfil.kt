package com.mili.eclipsereads

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

annotation class LogOregis

class Perfil : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- Início da Lógica de Perfil ---
        val prefs = requireActivity().getSharedPreferences("DADOS_USUARIO", Context.MODE_PRIVATE)
        val nomeUsuario = prefs.getString("NOME_USUARIO", "Usuário")
        val emailUsuario = prefs.getString("EMAIL_USUARIO", "usuario@email.com")

        val nomeTextView = view.findViewById<TextView>(R.id.textView23)
        val emailTextView = view.findViewById<TextView>(R.id.textView24)

        nomeTextView.text = nomeUsuario
        emailTextView.text = emailUsuario
        // --- Fim da Lógica de Perfil ---

        val sairButton = view.findViewById<Button>(R.id.button29)
        sairButton.setOnClickListener {
            // Limpar os dados do usuário ao sair (opcional, mas recomendado)
            val editor = prefs.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(requireActivity(), LogOregis::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
