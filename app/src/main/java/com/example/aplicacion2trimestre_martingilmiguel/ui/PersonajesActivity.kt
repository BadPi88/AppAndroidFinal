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

class PersonajesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonajesBinding
    var listaPersonajes = mutableListOf<Personaje>()
    var personajesAdapter = PersonajesAdapter(listaPersonajes)
   //no necesito variable api porque es publica

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

    }

    private fun setRecycler() {
        val layoutManager = GridLayoutManager(this, 1)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = personajesAdapter
        traerDatos()
    }

    private fun traerDatos() {
        lifecycleScope.launch(Dispatchers.IO) {
            val respuesta = ApiClient.apiClient.getPersonajes()

            withContext(Dispatchers.Main) {
                if (respuesta.isSuccessful) {
                    val listaP = respuesta.body()
                    if (listaP != null) {
                        // cargamos la lista en el adapter
                        personajesAdapter.listaPersonajes = listaP.listaPersonajes
                    }
                    personajesAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        this@PersonajesActivity,
                        "Error en la respuesta de la API",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}




