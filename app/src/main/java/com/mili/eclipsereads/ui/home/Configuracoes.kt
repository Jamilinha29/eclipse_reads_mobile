package com.mili.eclipsereads.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.mili.eclipsereads.R
import java.io.File

class Configuracoes : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_configuracoes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- Lógica de Alteração de Nome ---
        val prefs = requireActivity().getSharedPreferences("DADOS_USUARIO", Context.MODE_PRIVATE)
        val nomeUsuarioEditText = view.findViewById<TextInputEditText>(R.id.nome_usuario_edittext)
        val salvarNomeButton = view.findViewById<Button>(R.id.button_salvar_nome)

        // Carrega o nome de usuário salvo
        nomeUsuarioEditText.setText(prefs.getString("NOME_USUARIO", ""))

        salvarNomeButton.setOnClickListener {
            val novoNome = nomeUsuarioEditText.text.toString()
            if (novoNome.isNotBlank()) {
                prefs.edit().putString("NOME_USUARIO", novoNome).apply()
                Toast.makeText(requireContext(), "Nome salvo com sucesso!", Toast.LENGTH_SHORT).show()
                // Força a recriação da atividade para atualizar a UI
                requireActivity().recreate()
            } else {
                Toast.makeText(requireContext(), "O nome não pode estar em branco", Toast.LENGTH_SHORT).show()
            }
        }

        // --- Configuração de Tema ---
        val btnTemaClaro = view.findViewById<Button>(R.id.button30)
        val btnTemaEscuro = view.findViewById<Button>(R.id.button32)

        btnTemaClaro.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            requireActivity().recreate()
        }

        btnTemaEscuro.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            requireActivity().recreate()
        }

        // --- Configurações da Interface e Dados ---
        val switchNotificacoes = view.findViewById<SwitchMaterial>(R.id.switch_notificacoes)
        val switchSincronizacao = view.findViewById<SwitchMaterial>(R.id.switch_sincronizacao)
        val btnExportar = view.findViewById<Button>(R.id.button_exportar)
        val btnLimpar = view.findViewById<Button>(R.id.button_limpar)

        // Listener para Notificações
        switchNotificacoes.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // TODO: Adicionar lógica para ativar as notificações
                Toast.makeText(requireContext(), "Notificações ativadas", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: Adicionar lógica para desativar as notificações
                Toast.makeText(requireContext(), "Notificações desativadas", Toast.LENGTH_SHORT).show()
            }
        }

        // Listener para Sincronização Automática
        switchSincronizacao.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // TODO: Adicionar lógica para ativar a sincronização automática
                Toast.makeText(requireContext(), "Sincronização automática ativada", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: Adicionar lógica para desativar a sincronização automática
                Toast.makeText(requireContext(), "Sincronização automática desativada", Toast.LENGTH_SHORT).show()
            }
        }

        // Listener para Exportar Dados
        btnExportar.setOnClickListener {
            // TODO: Adicionar lógica para exportar os dados do usuário (ex: usando Storage Access Framework)
            Toast.makeText(requireContext(), "Exportando dados...", Toast.LENGTH_SHORT).show()
        }

        // Listener para Limpar Cache
        btnLimpar.setOnClickListener {
            showClearCacheConfirmationDialog()
        }
    }

    private fun showClearCacheConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Limpar Cache")
            .setMessage("Tem certeza de que deseja limpar o cache do aplicativo?")
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Limpar") { _, _ ->
                clearCache()
            }
            .show()
    }

    private fun clearCache() {
        try {
            val cacheDir = requireContext().cacheDir
            if (cacheDir != null && cacheDir.isDirectory) {
                deleteDir(cacheDir)
                Toast.makeText(requireContext(), "Cache limpo com sucesso", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Falha ao limpar o cache", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            if (children != null) {
                for (child in children) {
                    val success = deleteDir(File(dir, child))
                    if (!success) {
                        return false
                    }
                }
            }
            return dir.delete()
        } else if (dir != null && dir.isFile) {
            return dir.delete()
        } else {
            return false
        }
    }
}