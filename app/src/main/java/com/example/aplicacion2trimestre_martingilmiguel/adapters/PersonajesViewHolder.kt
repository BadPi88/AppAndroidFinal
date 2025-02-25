package com.example.aplicacion2trimestre_martingilmiguel.adapters


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacion2trimestre_martingilmiguel.databinding.ItemPersonajeBinding
import com.example.aplicacion2trimestre_martingilmiguel.model.Personaje
import com.squareup.picasso.Picasso


class PersonajesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemPersonajeBinding.bind(view)

    fun render(personaje: Personaje) {
        binding.tvName.text = personaje.name
        Picasso.get().load(personaje.image).into(binding.ivAvatar)
    }
}

