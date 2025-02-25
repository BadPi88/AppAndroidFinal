package com.example.aplicacion2trimestre_martingilmiguel.providers

import com.example.aplicacion2trimestre_martingilmiguel.model.ListaPersonajes
import retrofit2.Response
import retrofit2.http.GET

interface PersonajesInterfaz {
    @GET("character/")
    suspend fun getPersonajes(): Response<ListaPersonajes>
}

