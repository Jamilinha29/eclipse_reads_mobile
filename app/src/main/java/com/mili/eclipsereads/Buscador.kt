package com.mili.eclipsereads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Buscador : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_buscador, container, false)
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

        val searchView = view.findViewById<SearchView>(R.id.search_view_no_layout)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Ação a ser executada quando o usuário submeter a pesquisa (pressionar Enter)
                // Por exemplo, você pode iniciar a pesquisa de fato aqui.
                filterResults(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Ação a ser executada enquanto o usuário digita
                // Por exemplo, filtrar a lista de resultados em tempo real.
                filterResults(newText)
                return true
            }
        })
    }

    private fun filterResults(query: String?) {
        // Aqui você implementaria a lógica para filtrar os seus dados
        // com base na consulta de pesquisa (query).
        // Por exemplo, se você tiver uma lista de livros em um RecyclerView,
        // você atualizaria o adapter do RecyclerView com os resultados filtrados.

        // Exemplo de log (substitua pelo seu código de filtragem):
        if (!query.isNullOrEmpty()) {
            println("Pesquisando por: $query")
        } else {
            println("A pesquisa está vazia")
        }
    }
}
