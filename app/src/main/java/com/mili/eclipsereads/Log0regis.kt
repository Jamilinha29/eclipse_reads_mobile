package com.mili.eclipsereads

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.projeto2.Formulario_login
import com.example.projeto2.Formulario_registro

class Log0regis : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log0regis)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.form_placeholder, Formulario_login())
                .commit()
        }

        val botaoParaRegistro = findViewById<Button>(R.id.button9)
        botaoParaRegistro?.setOnClickListener {
            trocarFragment(Formulario_registro())
        }

        val botaoParaLogin = findViewById<Button>(R.id.button3)
        botaoParaLogin?.setOnClickListener {
            trocarFragment(Formulario_login())
        }
    }

    private fun trocarFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.form_placeholder, fragment)
            .commit()
    }
}
