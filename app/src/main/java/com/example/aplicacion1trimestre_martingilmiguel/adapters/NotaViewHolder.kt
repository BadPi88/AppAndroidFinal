package com.example.aplicacion1trimestre_martingilmiguel.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacion1trimestre_martingilmiguel.databinding.NotaLayoutBinding
import com.example.aplicacion1trimestre_martingilmiguel.models.NotaModel

class NotaViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val binding = NotaLayoutBinding.bind(v)

    fun render(
        nota: NotaModel,
        borrarNota: (Int) -> Unit,
        updateNota: (NotaModel) -> Unit
    ) {
        binding.tvNotaTitulo.text = nota.titulo
        binding.tvNotaContenido.text = nota.descripcion


        binding.btnBorrar.setOnClickListener {
            borrarNota(adapterPosition)
        }

        binding.btnActualizar.setOnClickListener {
            updateNota(nota)
        }
    }
}
