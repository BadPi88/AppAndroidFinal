package com.example.aplicacion2trimestre_martingilmiguel.providers.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.aplicacion2trimestre_martingilmiguel.db.AppBd
import com.example.aplicacion2trimestre_martingilmiguel.models.NotaModel

class CrudNotas {

    fun create(nota: NotaModel): Long {
        val db = AppBd.llave.writableDatabase
        return try {
            db.insertWithOnConflict(
                AppBd.TABLA_NOTAS,
                null,
                nota.toContentValues(),
                SQLiteDatabase.CONFLICT_IGNORE
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            -1L
        } finally {
            db.close()
        }
    }

    fun read(): MutableList<NotaModel> {
        val lista = mutableListOf<NotaModel>()
        val db = AppBd.llave.readableDatabase
        try {
            val cursor = db.query(
                AppBd.TABLA_NOTAS,
                arrayOf("id", "titulo", "descripcion"),
                null,
                null,
                null,
                null,
                null,
                null
            )
            while (cursor.moveToNext()) {
                val nota = NotaModel(
                    cursor.getInt(0),       // id
                    cursor.getString(1),    // titulo
                    cursor.getString(2)
                )
                lista.add(nota)
            }
            cursor.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            db.close()
        }
        return lista
    }

    fun borrar(id: Int): Boolean {
        val db = AppBd.llave.writableDatabase
        val filasBorradas = db.delete(AppBd.TABLA_NOTAS, "id=?", arrayOf(id.toString()))
        db.close()
        return filasBorradas > 0
    }

    fun actualizar(nota: NotaModel): Boolean {
        val db = AppBd.llave.writableDatabase
        val values = nota.toContentValues()
        var filasAfectadas = 0

        val query = "SELECT id FROM ${AppBd.TABLA_NOTAS} WHERE titulo = ? AND id <> ?"
        val cursor = db.rawQuery(query, arrayOf(nota.titulo, nota.id.toString()))
        val existeTitulo = cursor.moveToFirst()

        cursor.close()
        if (!existeTitulo) {
            filasAfectadas = db.update(
                AppBd.TABLA_NOTAS,
                values,
                "id = ?",
                arrayOf(nota.id.toString())
            )
        }
        db.close()
        return filasAfectadas > 0
    }

    fun borrarTodo() {
        val db = AppBd.llave.writableDatabase
        db.execSQL("DELETE FROM ${AppBd.TABLA_NOTAS}")
        db.close()
    }

    private fun NotaModel.toContentValues(): ContentValues {
        return ContentValues().apply {
            put("titulo", titulo)
            put("descripcion", descripcion)
        }
    }
}
