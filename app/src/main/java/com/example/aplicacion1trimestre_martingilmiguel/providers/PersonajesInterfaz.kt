package com.example.aplicacion1trimestre_martingilmiguel.providers

import com.example.aplicacion1trimestre_martingilmiguel.models.ListaPersonajes
import retrofit2.Response
import retrofit2.http.GET

interface PersonajesInterfaz {
    @GET("character/")
    suspend fun getPersonajes(): Response<ListaPersonajes>
}

