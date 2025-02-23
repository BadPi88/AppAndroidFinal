package com.example.aplicacion2trimestre_martingilmiguel.models

import com.google.gson.annotations.SerializedName


data class Personaje(
    val id: Int,
    val name: String,
    val species: String,
    val gender: String,
    val image: String,
)



data class ListaPersonajes(
    @SerializedName("results")val listaPersonajes: MutableList<Personaje>
)
