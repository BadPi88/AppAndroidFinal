package com.example.aplicacion2trimestre_martingilmiguel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplicacion2trimestre_martingilmiguel.adapters.AnuncioAdapter
import com.example.aplicacion2trimestre_martingilmiguel.databinding.ActivityTablonBinding
import com.example.aplicacion2trimestre_martingilmiguel.model.AnuncioModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class TablonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTablonBinding
    private lateinit var database: DatabaseReference
    private var listadoAnuncios = mutableListOf<AnuncioModel>()
    private lateinit var adapter: AnuncioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTablonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = FirebaseDatabase.getInstance().getReference("anuncios")

        setRecycler()
        setListeners()
    }

    private fun setRecycler() {
        binding.rvAnuncios.layoutManager = LinearLayoutManager(this)
        adapter = AnuncioAdapter(listadoAnuncios)
        binding.rvAnuncios.adapter = adapter
    }

    private fun setListeners() {
        binding.btnEnviar.setOnClickListener {
            val texto = binding.etMensaje.text.toString().trim()
            if (texto.isNotEmpty()) {
                publicarAnuncio(texto)
                binding.etMensaje.text.clear()
            }
        }

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //borramos la lista para luego clearla y que no se dupliquen los anuncios
                listadoAnuncios.clear()
                val tiempoActual = System.currentTimeMillis()
                val tiempoLimite = tiempoActual - (15 * 60 * 1000) // Hace 24 horas

                for (nodo in snapshot.children) {
                    val anuncioNodo = nodo.getValue(AnuncioModel::class.java)
                    val key = nodo.key // Obtener la clave del anuncio en Firebase

                    if (anuncioNodo != null) {
                        // Si el anuncio tiene más de 24 horas, lo eliminamos de Firebase
                        if (anuncioNodo.fecha < tiempoLimite) {
                            key?.let { database.child(it).removeValue() }
                        } else {
                            listadoAnuncios.add(anuncioNodo)
                        }
                    }
                }

                listadoAnuncios.sortByDescending { it.fecha }
                adapter.updateAdapter(listadoAnuncios)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TablonActivity, "Error cargando anuncios", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }


    private fun publicarAnuncio(texto: String) {
        val fecha = System.currentTimeMillis()
        val anuncio = AnuncioModel(texto, fecha)
        val key = fecha.toString()

        database.child(key).setValue(anuncio)
            .addOnSuccessListener {
                Toast.makeText(this, "Anuncio publicado", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "No se pudo publicar el anuncio", Toast.LENGTH_SHORT).show()
            }
    }
}
