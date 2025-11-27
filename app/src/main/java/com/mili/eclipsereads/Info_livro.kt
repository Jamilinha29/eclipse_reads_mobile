package com.mili.eclipsereads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class Info_livro : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_info_livro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainView = view.findViewById<View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        val addToLibraryButton = view.findViewById<Button>(R.id.button18)
        addToLibraryButton.setOnClickListener {
            // Adicione aqui a sua lógica para adicionar o livro à biblioteca
            Toast.makeText(requireContext(), "Livro adicionado à biblioteca", Toast.LENGTH_SHORT).show()
        }

        val readNowButton = view.findViewById<Button>(R.id.button17)
        readNowButton.setOnClickListener {
            // Adicione aqui a sua lógica para começar a ler o livro
            Toast.makeText(requireContext(), "Iniciando a leitura...", Toast.LENGTH_SHORT).show()
        }
    }
}
