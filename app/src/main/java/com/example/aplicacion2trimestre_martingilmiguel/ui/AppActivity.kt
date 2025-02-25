package com.example.aplicacion2trimestre_martingilmiguel.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicacion2trimestre_martingilmiguel.NotasActivity
import com.example.aplicacion2trimestre_martingilmiguel.R
import com.example.aplicacion2trimestre_martingilmiguel.ReproductorVideoActivity
import com.example.aplicacion2trimestre_martingilmiguel.databinding.ActivityAppBinding
import com.example.aplicacion2trimestre_martingilmiguel.ui.mapa.MapaActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth
        setListeners()
    }

    private fun setListeners() {

        binding.btnSalir.setOnClickListener {
            finishAffinity()
        }
        binding.btnCerrar.setOnClickListener {
            auth.signOut()
            finish()
        }
        binding.btnLocalizacion.setOnClickListener {
            val intent = Intent(this, MapaActivity::class.java)
            startActivity(intent)
        }
        binding.btnBuscador.setOnClickListener {
            val intent = Intent(this, BuscadorWebActivity::class.java)
            startActivity(intent)
        }
        binding.btnContactos.setOnClickListener {
            val intent = Intent(this, PersonajesActivity::class.java)
            startActivity(intent)
        }
        binding.btnPerfil.setOnClickListener {
            val intent = Intent(this, PerfilUsuarioActivity::class.java)
            startActivity(intent)
        }

        binding.btnNotas.setOnClickListener {
            val intent = Intent(this, NotasActivity::class.java)
            startActivity(intent)
        }
        binding.btnOpcion1.setOnClickListener {
            val intent = Intent(this, SensorLuzActivity::class.java)
            startActivity(intent)
        }
        binding.btnOpcion2.setOnClickListener {
            val intent = Intent(this, ReproductorVideoActivity::class.java)
            startActivity(intent)
        }
    }
}
