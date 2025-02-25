package com.example.aplicacion2trimestre_martingilmiguel.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacion2trimestre_martingilmiguel.databinding.AnuncioLayoutBinding
import com.example.aplicacion2trimestre_martingilmiguel.model.AnuncioModel
import java.text.SimpleDateFormat
import java.util.Date

class AnuncioViewHolder(v: View): RecyclerView.ViewHolder(v) {
    val binding = AnuncioLayoutBinding.bind(v)

    fun render(item: AnuncioModel) {
        binding.tvTexto.text = item.mensaje
        binding.tvFecha.text = fechaFormateada(item.fecha)
    }

    private fun fechaFormateada(fecha: Long): String {
        val date= Date(fecha)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        return format.format(date)
    }
}
