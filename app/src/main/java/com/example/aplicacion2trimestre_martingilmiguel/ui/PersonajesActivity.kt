package com.example.aplicacion2trimestre_martingilmiguel.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aplicacion2trimestre_martingilmiguel.adapters.PersonajesAdapter
import com.example.aplicacion2trimestre_martingilmiguel.databinding.ActivityPersonajesBinding
import com.example.aplicacion2trimestre_martingilmiguel.model.Personaje
import com.example.aplicacion2trimestre_martingilmiguel.providers.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.aplicacion2trimestre_martingilmiguel.viewmodel.PersonajesViewModel


class PersonajesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonajesBinding
    private val personajesViewModel: PersonajesViewModel by viewModels()
    private val personajesAdapter = PersonajesAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonajesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setRecycler()
        observarDatos()

        // Cargar los datos al iniciar la Activity
        personajesViewModel.traerDatos()
    }

    private fun setRecycler() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)
        binding.recyclerView.adapter = personajesAdapter
    }

    private fun observarDatos() {
        personajesViewModel.listaPersonajes.observe(this, Observer { personajes ->
            personajesAdapter.listaPersonajes = personajes.toMutableList()
            personajesAdapter.notifyDataSetChanged()
        })

        personajesViewModel.error.observe(this, Observer { mensaje ->
            mensaje?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
