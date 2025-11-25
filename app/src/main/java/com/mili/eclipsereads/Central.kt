package com.mili.eclipsereads

import android.os.Bundle
import android.widget.ImageView // Importação necessária
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit // Necessário para a sintaxe DSL
import androidx.fragment.app.replace // Necessário para a sintaxe DSL

class Central : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_central)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_central, Inicio())
                .commit()
        }


        findViewById<ImageView>(R.id.Buscar).setOnClickListener {
            supportFragmentManager.commit {
                replace<Inicio>(R.id.fragment_central)
                setReorderingAllowed(true)

            }
        }

        findViewById<ImageView>(R.id.buscar0).setOnClickListener {
            supportFragmentManager.commit {
                replace<Buscador>(R.id.fragment_central)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }


        findViewById<ImageView>(R.id.buscar00).setOnClickListener {
            supportFragmentManager.commit {
                replace<Minha_biblioteca>(R.id.fragment_central)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        findViewById<ImageView>(R.id.buscar000).setOnClickListener {
            supportFragmentManager.commit {
                replace<Configuracoes>(R.id.fragment_central)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        findViewById<ImageView>(R.id.buscar0000).setOnClickListener {
            supportFragmentManager.commit {
                replace<Perfil>(R.id.fragment_central)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

    }
}
