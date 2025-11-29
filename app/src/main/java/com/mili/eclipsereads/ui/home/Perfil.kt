package com.mili.eclipsereads.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.mili.eclipsereads.R
import com.mili.eclipsereads.ui.login.Formulario_login

class Perfil : Fragment() {

    private lateinit var avatarImage: ShapeableImageView
    private lateinit var bannerImage: ImageView
    private var isBannerSelected: Boolean = false

    // Nova API para obter o resultado da galeria de forma segura.
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val selectedImageUri: Uri? = result.data?.data
            if (selectedImageUri != null) {
                // Salva e exibe a imagem selecionada
                saveAndLoadImage(selectedImageUri)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        avatarImage = view.findViewById(R.id.avatar_image)
        bannerImage = view.findViewById(R.id.banner_image)

        avatarImage.setOnClickListener {
            isBannerSelected = false
            openGallery()
        }
        bannerImage.setOnClickListener {
            isBannerSelected = true
            openGallery()
        }

        loadProfileData(view)

        val sairButton = view.findViewById<Button>(R.id.button29)
        sairButton.setOnClickListener {
            val prefs = requireActivity().getSharedPreferences("DADOS_USUARIO", Context.MODE_PRIVATE)
            prefs.edit().clear().apply()

            val intent = Intent(requireActivity(), Formulario_login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun loadProfileData(view: View) {
        val userPrefs = requireActivity().getSharedPreferences("DADOS_USUARIO", Context.MODE_PRIVATE)
        val bookPrefs = requireActivity().getSharedPreferences("DADOS_LIVROS", Context.MODE_PRIVATE)

        // Carrega nome e email
        view.findViewById<TextView>(R.id.textView23).text = userPrefs.getString("NOME_USUARIO", "Usuário")
        view.findViewById<TextView>(R.id.textView24).text = userPrefs.getString("EMAIL_USUARIO", "usuario@email.com")

        // Carrega imagens salvas do avatar e banner
        userPrefs.getString("AVATAR_URI", null)?.let {
            Glide.with(this).load(Uri.parse(it)).into(avatarImage)
        }
        userPrefs.getString("BANNER_URI", null)?.let {
            Glide.with(this).load(Uri.parse(it)).into(bannerImage)
        }

        // Carrega estatísticas
        val favorites = bookPrefs.getStringSet("FAVORITES", emptySet()) ?: emptySet()
        val read = bookPrefs.getStringSet("READ", emptySet()) ?: emptySet()
        val reading = bookPrefs.getStringSet("READING", emptySet()) ?: emptySet()
        val dropped = bookPrefs.getStringSet("DROPPED", emptySet()) ?: emptySet()

        val statsGrid = view.findViewById<GridLayout>(R.id.stats_grid)
        val totalBooks = favorites.size + read.size + reading.size + dropped.size

        if (totalBooks > 0) {
            statsGrid.visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.favoritos_count).text = favorites.size.toString()
            view.findViewById<TextView>(R.id.lidos_count).text = read.size.toString()
            view.findViewById<TextView>(R.id.lendo_count).text = reading.size.toString()
            view.findViewById<TextView>(R.id.dropados_count).text = dropped.size.toString()
        } else {
            statsGrid.visibility = View.GONE
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun saveAndLoadImage(uri: Uri) {
        val prefs = requireActivity().getSharedPreferences("DADOS_USUARIO", Context.MODE_PRIVATE).edit()
        val targetImageView = if (isBannerSelected) {
            prefs.putString("BANNER_URI", uri.toString())
            bannerImage
        } else {
            prefs.putString("AVATAR_URI", uri.toString())
            avatarImage
        }
        prefs.apply()

        // Usa Glide para carregar a imagem de forma eficiente
        Glide.with(this).load(uri).into(targetImageView)
    }

    override fun onResume() {
        super.onResume()
        view?.let { loadProfileData(it) }
    }

    // A função onActivityResult foi removida pois agora usamos a Activity Result API.
}