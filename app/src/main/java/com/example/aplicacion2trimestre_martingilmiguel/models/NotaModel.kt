package com.example.aplicacion2trimestre_martingilmiguel.models

import java.io.Serializable

data class NotaModel(
    val id: Int,
    val titulo: String,
    val descripcion: String,
): Serializable
