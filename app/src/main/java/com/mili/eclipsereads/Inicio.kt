package com.mili.eclipsereads

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Inicio : Fragment() {

    private lateinit var continueReadingRecyclerView: RecyclerView
    private lateinit var dailyUpdatesRecyclerView: RecyclerView
    private lateinit var emptyContinueReading: TextView
    private lateinit var emptyDailyUpdates: TextView

    // Supondo que você tenha uma classe de dados Book
    data class Book(val title: String, val author: String, val cover: Int)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_inicio, container, false)
        continueReadingRecyclerView = view.findViewById(R.id.continue_reading_recycler_view)
        dailyUpdatesRecyclerView = view.findViewById(R.id.daily_updates_recycler_view)
        emptyContinueReading = view.findViewById(R.id.empty_continue_reading)
        emptyDailyUpdates = view.findViewById(R.id.empty_daily_updates)
        return view
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

        // --- Início da Lógica de Boas-Vindas ---
        val prefs = requireActivity().getSharedPreferences("DADOS_USUARIO", Context.MODE_PRIVATE)
        val nomeUsuario = prefs.getString("NOME_USUARIO", "Usuário") // "Usuário" é o valor padrão

        val welcomeTextView = view.findViewById<TextView>(R.id.textView7)
        welcomeTextView.text = "Olá, $nomeUsuario!"
        // --- Fim da Lógica de Boas-Vindas ---

        setupRecyclerViews()
    }

    private fun setupRecyclerViews() {
        continueReadingRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        dailyUpdatesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Exemplo com lista de livros. Em um aplicativo real, isso viria de um ViewModel ou de uma API.
        val continueReadingBooks = listOf(
            Book("Quarta capa", "Autor", R.drawable.capa),
            Book("Outro Livro", "Outro Autor", R.drawable.capa),
            Book("Mais um Livro", "Mais um Autor", R.drawable.capa)
        )

        val dailyUpdatesBooks = emptyList<Book>() // Exemplo de lista vazia

        // Configura o RecyclerView "Continue Lendo"
        if (continueReadingBooks.isEmpty()) {
            continueReadingRecyclerView.visibility = View.GONE
            emptyContinueReading.visibility = View.VISIBLE
        } else {
            continueReadingRecyclerView.visibility = View.VISIBLE
            emptyContinueReading.visibility = View.GONE
            continueReadingRecyclerView.adapter = BookAdapter(continueReadingBooks)
        }

        // Configura o RecyclerView "Atualizações do Dia"
        if (dailyUpdatesBooks.isEmpty()) {
            dailyUpdatesRecyclerView.visibility = View.GONE
            emptyDailyUpdates.visibility = View.VISIBLE
        } else {
            dailyUpdatesRecyclerView.visibility = View.VISIBLE
            emptyDailyUpdates.visibility = View.GONE
            dailyUpdatesRecyclerView.adapter = BookAdapter(dailyUpdatesBooks)
        }
    }

    // Supondo que você tenha um BookAdapter como este
    class BookAdapter(private val books: List<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

        class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            // Referências para as views do item do livro, se houver
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book_simple, parent, false)
            return BookViewHolder(view)
        }

        override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
            // Lógica para preencher os dados do livro no ViewHolder
        }

        override fun getItemCount() = books.size
    }
}
