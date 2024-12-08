package com.example.aplicacion1trimestre_martingilmiguel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicacion1trimestre_martingilmiguel.databinding.ActivityPerfilUsuarioBinding

class PerfilUsuarioActivity : AppCompatActivity() {
    //componentes a guardar datos
    private lateinit var etNombre: EditText
    private lateinit var spinnerOpciones: Spinner
    private lateinit var sbAltura: SeekBar
    private lateinit var cbOption1: CheckBox
    private lateinit var rbOption1: RadioButton
    private lateinit var rbOption2: RadioButton
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button

    private lateinit var binding: ActivityPerfilUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilUsuarioBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeViews()
        CargarDatos()
        buttonListeners()
    }

    private fun initializeViews() {
        etNombre = findViewById(R.id.etNombre)
        spinnerOpciones = findViewById(R.id.spinnerOpciones)
        sbAltura = findViewById(R.id.sbAltura)
        cbOption1 = findViewById(R.id.cbOption1)
        rbOption1 = findViewById(R.id.rbOption1)
        rbOption2 = findViewById(R.id.rbOption2)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCancelar = findViewById(R.id.btnCancelar)

        // Configuración del Spinner
        val opcionesSpinner = listOf("Desarrollador", "Administrador de Sistemas", "Soporte Técnico")
        //adaptador
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesSpinner)

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerOpciones.adapter = spinnerAdapter
    }

    private fun CargarDatos() {
        val sharedPrefs = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)

        etNombre.setText(sharedPrefs.getString("nombre", ""))

        val spinnerPosicion = sharedPrefs.getInt("spinnerPosition", 0)
        spinnerOpciones.setSelection(spinnerPosicion)

        sbAltura.progress = sharedPrefs.getInt("altura", 0)

        cbOption1.isChecked = sharedPrefs.getBoolean("checkboxChecked", false)

        when (sharedPrefs.getInt("radioSelection", 0)) {
            1 -> rbOption1.isChecked = true
            2 -> rbOption2.isChecked = true
        }
    }

    // Listeners para los botones
    private fun buttonListeners() {
        btnGuardar.setOnClickListener {

            // guardar datos SharedPreferences
            val sharedPrefs = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()

            // Para hacer save
            editor.putString("nombre", etNombre.text.toString())

            editor.putInt("spinnerPosition", spinnerOpciones.selectedItemPosition)

            editor.putInt("altura", sbAltura.progress)

            editor.putBoolean("checkboxChecked", cbOption1.isChecked)

            // guardar radio button
            val radioSelection = when {
                rbOption1.isChecked -> 1
                rbOption2.isChecked -> 2
                else -> 0
            }
            editor.putInt("radioSelection", radioSelection)

            editor.apply()

            Toast.makeText(this, "Perfil guardado", Toast.LENGTH_SHORT).show()

            //abrir activity de perfil guardado
            val intent = Intent(this, PerfilGuardadoActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }
}
