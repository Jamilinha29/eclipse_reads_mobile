package com.mili.eclipsereads.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mili.eclipsereads.ui.details.Info_livro
import com.mili.eclipsereads.R

class Inicio : Fragment() {

    private lateinit var continueReadingRecyclerView: RecyclerView
    private lateinit var dailyUpdatesRecyclerView: RecyclerView
    private lateinit var emptyContinueReading: TextView
    private lateinit var emptyDailyUpdates: TextView

    data class Book(val id: String, val title: String, val author: String, val cover: Int)

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

        val prefs = requireActivity().getSharedPreferences("DADOS_USUARIO", Context.MODE_PRIVATE)
        val nomeUsuario = prefs.getString("NOME_USUARIO", "Usuário")

        val welcomeTextView = view.findViewById<TextView>(R.id.textView7)
        welcomeTextView.text = "Olá, $nomeUsuario!"

        setupRecyclerViews()

        val verMaisButton1 = view.findViewById<Button>(R.id.button100)
        val verMaisButton2 = view.findViewById<Button>(R.id.button10)

        val navigateToBuscador = View.OnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_central, Buscador())
                .addToBackStack(null)
                .commit()
        }

        verMaisButton1.setOnClickListener(navigateToBuscador)
        verMaisButton2.setOnClickListener(navigateToBuscador)
    }

    private fun setupRecyclerViews() {
        continueReadingRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        dailyUpdatesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val continueReadingBooks = listOf(
            Book("book_4", "Livro em Andamento", "Autor", R.drawable.capa)
        )

        val dailyUpdatesBooks = emptyList<Book>()

        val continueReadingAdapter = BookAdapter(continueReadingBooks) { book ->
            // TODO: Navegar para a página exata onde a leitura foi interrompida
            navigateToBookDetail(book.id, true)
        }

        val dailyUpdatesAdapter = BookAdapter(dailyUpdatesBooks) { book ->
            navigateToBookDetail(book.id)
        }

        if (continueReadingBooks.isNotEmpty()) {
            continueReadingRecyclerView.visibility = View.VISIBLE
            emptyContinueReading.visibility = View.GONE
            continueReadingRecyclerView.adapter = continueReadingAdapter
        } else {
            continueReadingRecyclerView.visibility = View.GONE
            emptyContinueReading.visibility = View.VISIBLE
        }

        if (dailyUpdatesBooks.isNotEmpty()) {
            dailyUpdatesRecyclerView.visibility = View.VISIBLE
            emptyDailyUpdates.visibility = View.GONE
            dailyUpdatesRecyclerView.adapter = dailyUpdatesAdapter
        } else {
            dailyUpdatesRecyclerView.visibility = View.GONE
            emptyDailyUpdates.visibility = View.VISIBLE
        }
    }

    private fun navigateToBookDetail(bookId: String, continueReading: Boolean = false) {
        val fragment = if (continueReading) {
            // TODO: Substituir por um fragmento que represente a leitura em andamento
            LeituraFragment().apply {
                arguments = Bundle().apply {
                    putString("BOOK_ID", bookId)
                }
            }
        } else {
            Info_livro().apply {
                arguments = Bundle().apply {
                    putString("BOOK_ID", bookId)
                }
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_central, fragment)
            .addToBackStack(null)
            .commit()
    }

    class BookAdapter(private val books: List<Book>, private val onItemClick: (Book) -> Unit) :
        RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

        class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            // Referências para as views do item do livro
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book_simple, parent, false)
            return BookViewHolder(view)
        }

        override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
            val book = books[position]
            holder.itemView.setOnClickListener {
                onItemClick(book)
            }
        }

        override fun getItemCount() = books.size
    }

    // Fragmento de exemplo para a tela de leitura
    class LeituraFragment : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View? {
            // TODO: Inflar o layout da tela de leitura e carregar o livro
            return TextView(requireContext()).apply { text = "Tela de Leitura" }
        }
    }
}