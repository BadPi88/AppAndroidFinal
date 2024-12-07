package com.example.aplicacion1trimestre_martingilmiguel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplicacion1trimestre_martingilmiguel.adapters.NotaAdapter
import com.example.aplicacion1trimestre_martingilmiguel.databinding.ActivityNotasBinding
import com.example.aplicacion1trimestre_martingilmiguel.db.MyDatabase
import com.example.aplicacion1trimestre_martingilmiguel.models.NotaModel
import com.example.aplicacion1trimestre_martingilmiguel.notas.AddNotasActivity
import com.example.aplicacion1trimestre_martingilmiguel.providers.db.CrudNotas

class NotasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotasBinding
    private var lista = mutableListOf<NotaModel>()
    private lateinit var adapter: NotaAdapter
    lateinit var contexto: Context
    private lateinit var myDatabase: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotasBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        contexto = this
        myDatabase = MyDatabase(this)

        setListeners()
        setRecycler()
        title = "Mi Bloc de Notas"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notas, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItem_salir -> {
                finish()
            }
            R.id.menuItem_borrar -> {
                confirmarBorrado()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmarBorrado() {
        val builder = AlertDialog.Builder(this)
            .setTitle("¿Borrar todas las notas?")
            .setMessage("¿Seguro que deseas borrar todas las notas?")
            .setNegativeButton("CANCELAR") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("ACEPTAR") { _, _ ->
                CrudNotas().borrarTodo()
                setRecycler()
            }
            .create()
            .show()
    }

    private fun traerRegistros() {
        lista = CrudNotas().read()

    }

    private fun setRecycler() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerNotas.layoutManager = layoutManager
        traerRegistros()
        adapter = NotaAdapter(lista, { posicion -> borrarNota(posicion) }, { nota -> update(nota) })
        binding.recyclerNotas.adapter = adapter
    }

    private fun update(n: NotaModel) {
        val i = Intent(this, AddNotasActivity::class.java).apply {
             putExtra("NOTA", n)
        }
        startActivity(i)
    }

    private fun borrarNota(position: Int) {
        val id = lista[position].id
        lista.removeAt(position)
        if (CrudNotas().borrar(id)) {
            adapter.notifyItemRemoved(position)
            Toast.makeText(this, "Nota eliminada correctamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No se pudo eliminar la nota", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setListeners() {
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddNotasActivity::class.java))
        }
    }

    override fun onRestart() {
        super.onRestart()
        setRecycler()
    }
}
