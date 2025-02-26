package com.example.aplicacion2trimestre_martingilmiguel.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicacion2trimestre_martingilmiguel.databinding.ActivityPerfilUsuarioBinding

class PerfilUsuarioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilUsuarioBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupSeekBar()
        loadUserData()
        setListeners()
    }

    private fun setupSeekBar() {
        binding.sbAltura.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvAltura.text = "Estatura: $progress cm"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        val opcionesSpinner = listOf("Desarrollador", "Administrador de Sistemas", "Soporte Técnico")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesSpinner)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOpciones.adapter = spinnerAdapter
    }

    private fun loadUserData() {
        val sharedPrefs = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)

        binding.etNombre.setText(sharedPrefs.getString("nombre", ""))
        binding.spinnerOpciones.setSelection(sharedPrefs.getInt("spinnerPosition", 0))
        binding.sbAltura.progress = sharedPrefs.getInt("altura", 0)
        binding.cbOption1.isChecked = sharedPrefs.getBoolean("checkboxChecked", false)
    }

    private fun setListeners() {
        binding.btnGuardar.setOnClickListener {
            val sharedPrefs = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
            with(sharedPrefs.edit()) {
                putString("nombre", binding.etNombre.text.toString())
                putInt("spinnerPosition", binding.spinnerOpciones.selectedItemPosition)
                putInt("altura", binding.sbAltura.progress)
                putBoolean("checkboxChecked", binding.cbOption1.isChecked)
                apply()
            }

            Toast.makeText(this, "Perfil guardado", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, PerfilGuardadoActivity::class.java))
            finish()
        }

        binding.btnCancelar.setOnClickListener {
            finish()
        }
    }
}
