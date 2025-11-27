package com.mili.eclipsereads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Buscador : Fragment() {

    // Supondo que você tenha uma classe de dados Book
    data class Book(val id: String, val title: String, val author: String, val cover: Int)

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

        // Configuração do SearchView
        val searchView = view.findViewById<SearchView>(R.id.search_view_no_layout)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterResults(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterResults(newText)
                return true
            }
        })

        // Configuração dos RecyclerViews
        setupRecyclerViews(view)
    }

    private fun setupRecyclerViews(view: View) {
        val recommendedRecyclerView = view.findViewById<RecyclerView>(R.id.recommended_recycler_view)
        val mostReadRecyclerView = view.findViewById<RecyclerView>(R.id.most_read_recycler_view)
        val newAdditionsRecyclerView = view.findViewById<RecyclerView>(R.id.new_additions_recycler_view)

        recommendedRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        mostReadRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        newAdditionsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Exemplo com lista de livros
        val books = listOf(
            Book("book_1", "Quarta capa", "Autor", R.drawable.capa),
            Book("book_2", "Outro Livro", "Outro Autor", R.drawable.capa),
            Book("book_3", "Mais um Livro", "Mais um Autor", R.drawable.capa)
        )

        val adapter = BookAdapter(books) { book ->
            navigateToBookDetail(book.id)
        }

        recommendedRecyclerView.adapter = adapter
        mostReadRecyclerView.adapter = adapter
        newAdditionsRecyclerView.adapter = adapter
    }

    private fun filterResults(query: String?) {
        // TODO: Implementar a lógica de filtragem do adapter
        println("Pesquisando por: $query")
    }

    private fun navigateToBookDetail(bookId: String) {
        val fragment = Info_livro().apply {
            arguments = Bundle().apply {
                putString("BOOK_ID", bookId)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_central, fragment)
            .addToBackStack(null)
            .commit()
    }

    // Adapter para os livros
    class BookAdapter(private val books: List<Book>, private val onItemClick: (Book) -> Unit) : 
        RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

        class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            // TODO: Referenciar as views do item do livro (título, autor, capa)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book_cover, parent, false)
            return BookViewHolder(view)
        }

        override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
            val book = books[position]
            // TODO: Preencher os dados do livro no ViewHolder

            holder.itemView.setOnClickListener { 
                onItemClick(book)
            }
        }

        override fun getItemCount() = books.size
    }
}
