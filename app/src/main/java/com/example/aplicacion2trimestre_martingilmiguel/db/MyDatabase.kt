package com.example.aplicacion2trimestre_martingilmiguel.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabase(private val context: Context) : SQLiteOpenHelper(AppBd.contexto, AppBd.DB, null, AppBd.VERSION) {


    // Query para crear la tabla de notas
    private val q = "create table ${AppBd.TABLA_NOTAS}(" +
            "id integer primary key autoincrement," +
            "titulo text not null," +
            "descripcion text not null);"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(q)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) {
            val borrarTabla = "DROP TABLE IF EXISTS ${AppBd.TABLA_NOTAS}"
            db?.execSQL(borrarTabla)
            onCreate(db)
        }
    }
}
