package com.example.aplicacion1trimestre_martingilmiguel.providers

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiClient: PersonajesInterfaz = retrofit.create(PersonajesInterfaz::class.java)
}
