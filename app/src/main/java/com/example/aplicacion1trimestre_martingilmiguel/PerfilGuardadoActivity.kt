package com.example.aplicacion1trimestre_martingilmiguel

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicacion1trimestre_martingilmiguel.databinding.ActivityPerfilGuardadoBinding

class PerfilGuardadoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilGuardadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPerfilGuardadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        displayProfileData()
    }

    private fun displayProfileData() {
        // get SharedPreferences
        val sharedPrefs = getSharedPreferences("UserProfile", MODE_PRIVATE)

        // Encontrar TextViews en el diseño
        val tvNombre = findViewById<TextView>(R.id.tvNombreDisplay)
        val tvSpinner = findViewById<TextView>(R.id.tvSpinnerDisplay)
        val tvAltura = findViewById<TextView>(R.id.tvAlturaDisplay)
        val tvCheckbox = findViewById<TextView>(R.id.tvCheckboxDisplay)
        val tvRadio = findViewById<TextView>(R.id.tvRadioDisplay)

        // Mostrar datos en los TextViews
        tvNombre.text = "Nombre: ${sharedPrefs.getString("nombre", "No guardado")}"


        tvSpinner.text = "Selección: ${sharedPrefs.getInt("spinnerPosition", 0)}"

        tvAltura.text = "Altura: ${sharedPrefs.getInt("altura", 0)}"

        val checkboxPulsada =
            if (sharedPrefs.getBoolean("checkboxChecked", false)) "Marcado" else "No marcado"
        tvCheckbox.text = "Checkbox: $checkboxPulsada"

        val radioSelection = when (sharedPrefs.getInt("radioSelection", 0)) {
            1 -> "Opción 1"
            2 -> "Opción 2"
            else -> "No seleccionado"
        }
        // Mostrar la selección del radio button
        tvRadio.text = "Radio: $radioSelection"
    }
}