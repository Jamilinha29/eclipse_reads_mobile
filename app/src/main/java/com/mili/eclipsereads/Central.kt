package com.mili.eclipsereads

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit // Necessário para a sintaxe DSL
import androidx.fragment.app.replace // Necessário para a sintaxe DSL
import com.google.android.material.bottomnavigation.BottomNavigationView

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
            supportFragmentManager.commit {
                replace<Inicio>(R.id.fragment_central)
                setReorderingAllowed(true)
            }
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.commit {
                        replace<Inicio>(R.id.fragment_central)
                        setReorderingAllowed(true)
                    }
                    true
                }
                R.id.navigation_search -> {
                    supportFragmentManager.commit {
                        replace<Buscador>(R.id.fragment_central)
                        setReorderingAllowed(true)
                        addToBackStack(null)
                    }
                    true
                }
                R.id.navigation_library -> {
                    supportFragmentManager.commit {
                        replace<Minha_biblioteca>(R.id.fragment_central)
                        setReorderingAllowed(true)
                        addToBackStack(null)
                    }
                    true
                }
                R.id.navigation_config -> {
                    supportFragmentManager.commit {
                        replace<Configuracoes>(R.id.fragment_central)
                        setReorderingAllowed(true)
                        addToBackStack(null)
                    }
                    true
                }
                R.id.navigation_profile -> {
                    supportFragmentManager.commit {
                        replace<Perfil>(R.id.fragment_central)
                        setReorderingAllowed(true)
                        addToBackStack(null)
                    }
                    true
                }
                else -> false
            }
        }
    }
}
