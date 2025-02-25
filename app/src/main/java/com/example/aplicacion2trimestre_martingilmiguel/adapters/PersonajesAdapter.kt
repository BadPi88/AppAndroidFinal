package com.example.aplicacion2trimestre_martingilmiguel.adapters

import com.example.aplicacion2trimestre_martingilmiguel.R
import com.example.aplicacion2trimestre_martingilmiguel.model.Personaje
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class PersonajesAdapter(
    var listaPersonajes: List<Personaje>
) : RecyclerView.Adapter<PersonajesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_personaje, parent, false)

        return PersonajesViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonajesViewHolder, position: Int) {
        val personaje = listaPersonajes[position]
        holder.render(personaje)

    }

    override fun getItemCount() = listaPersonajes.size

}
