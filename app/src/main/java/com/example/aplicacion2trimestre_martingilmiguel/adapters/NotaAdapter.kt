package com.example.aplicacion2trimestre_martingilmiguel.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacion2trimestre_martingilmiguel.R
import com.example.aplicacion2trimestre_martingilmiguel.model.NotaModel

class NotaAdapter(

    var lista: MutableList<NotaModel>,
    private var borrarNota: (Int) -> Unit,
    private val updateNota: (NotaModel) -> Unit,
) : RecyclerView.Adapter<NotaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.nota_layout, parent, false)
        return NotaViewHolder(v)
    }

    override fun getItemCount() = lista.size

    // renderiza cada nota
    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        holder.render(lista[position], borrarNota, updateNota)
    }
}
