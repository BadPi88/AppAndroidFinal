package com.example.aplicacion2trimestre_martingilmiguel.ui.notas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicacion2trimestre_martingilmiguel.R
import com.example.aplicacion2trimestre_martingilmiguel.databinding.ActivityAddNotasBinding
import com.example.aplicacion2trimestre_martingilmiguel.models.NotaModel
import com.example.aplicacion2trimestre_martingilmiguel.providers.db.CrudNotas

class AddNotasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNotasBinding
    private var titulo = ""
    private var descripcion = ""
    private var id = 1
    private var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotasBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recogerNota()

        setListeners()

        if (isUpdate) {
            binding.tvTitle.text = "Editar Nota"
            binding.btnGuardar.text = "Editar"
        } else {
            binding.tvTitle.text = "Crear Nota"
        }
    }

    private fun recogerNota() {
        intent.extras?.let { datos ->
            val nota = datos.getSerializable("NOTA") as? NotaModel
            nota?.let {
                isUpdate = true
                titulo = it.titulo
                descripcion = it.descripcion
                id = it.id
                pintarDatos()
            }
        }
    }

    private fun pintarDatos() {
        binding.etTitulo.setText(titulo)
        binding.etContenido.setText(descripcion)
    }

    private fun setListeners() {
        binding.btnCancelar.setOnClickListener {
            finish()
        }

        binding.btnGuardar.setOnClickListener {
            guardarRegistro()
        }
    }

    private fun guardarRegistro() {
        if (datosCorrectos()) {
            val nota = NotaModel(id, titulo, descripcion)
            if (!isUpdate) {
                if (CrudNotas().create(nota) != -1L) {
                    Toast.makeText(this, "Nota añadida correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al añadir la nota", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (CrudNotas().actualizar(nota)) {
                    Toast.makeText(this, "Nota actualizada", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al actualizar la nota", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun datosCorrectos(): Boolean {
        titulo = binding.etTitulo.text.toString().trim()
        descripcion = binding.etContenido.text.toString().trim()

        if (titulo.length < 3) {
            binding.etTitulo.error = "El campo título debe tener más de 3 caracteres"
            return false
        }
        if (descripcion.isEmpty()) {
            binding.etContenido.error = "El contenido no puede estar vacío"
            return false
        }
        return true
    }

}