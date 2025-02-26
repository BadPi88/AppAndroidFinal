package com.example.aplicacion2trimestre_martingilmiguel.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicacion2trimestre_martingilmiguel.databinding.ActivityPerfilGuardadoBinding

class PerfilGuardadoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilGuardadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPerfilGuardadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mostrarDatos()
    }

    private fun mostrarDatos() {
        val sharedPrefs = getSharedPreferences("UserProfile", MODE_PRIVATE)

        binding.tvNombreDisplay.text = "Nombre: ${sharedPrefs.getString("nombre", "No guardado")}"
        binding.tvSpinnerDisplay.text = "Departamento: ${sharedPrefs.getInt("spinnerPosition", 1)}"
        binding.tvAlturaDisplay.text = "Altura: ${sharedPrefs.getInt("altura", 0)}"
        binding.tvCheckboxDisplay.text = "Suscribirse a Noticias: ${
            if (sharedPrefs.getBoolean("checkboxChecked", false)) "Sí" else "No"
        }"
    }
}
