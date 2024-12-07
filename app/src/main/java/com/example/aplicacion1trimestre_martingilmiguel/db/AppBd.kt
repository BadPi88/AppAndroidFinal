package com.example.aplicacion1trimestre_martingilmiguel.db

import android.app.Application
import android.content.Context

class AppBd : Application() {

    companion object{
        //Si se aumenta la version se borraran todos los registros

        const val VERSION= 1
        const val DB="Base_1"
        const val TABLA_NOTAS = "notas"
        lateinit var contexto: Context
        lateinit var llave: MyDatabase
    }

    override fun onCreate() {
        super.onCreate()
        contexto = applicationContext
        llave = MyDatabase(this)
    }
}