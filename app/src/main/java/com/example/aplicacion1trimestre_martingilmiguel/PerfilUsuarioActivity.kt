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
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button
    private lateinit var tvAltura: TextView

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
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCancelar = findViewById(R.id.btnCancelar)


        sbAltura = findViewById(R.id.sbAltura)
        tvAltura = findViewById(R.id.tvAltura)

        sbAltura.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Mostrar el valor de la SeekBar en el TextView
                tvAltura.text = " Estatura: $progress cm"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })


        // Configuración del Spinner
        val opcionesSpinner =
            listOf("Desarrollador", "Administrador de Sistemas", "Soporte Técnico")
        //adaptador
        val spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesSpinner)

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
