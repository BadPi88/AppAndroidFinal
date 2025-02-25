package com.example.aplicacion2trimestre_martingilmiguel.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacion2trimestre_martingilmiguel.R
import com.example.aplicacion2trimestre_martingilmiguel.model.AnuncioModel



class AnuncioAdapter(var lista: MutableList<AnuncioModel>) : RecyclerView.Adapter<AnuncioViewHolder>() {

    fun updateAdapter(listaNueva: MutableList<AnuncioModel>) {
        lista = listaNueva
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnuncioViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.anuncio_layout, parent, false)
        return AnuncioViewHolder(v)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: AnuncioViewHolder, position: Int) {
        holder.render(lista[position])
    }
}
