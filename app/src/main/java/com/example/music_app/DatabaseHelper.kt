package com.example.music_app

import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf

class DatabaseHelper(context : Context) : SQLiteOpenHelper(

    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION

){

    companion object{
        private const val DATABASE_NAME = "musica.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USUARIO = "usuarios"
        private const val COL_ID = "id"
        private const val COL_NOMBRE = "nombre"
        private const val COL_CORREO = "correo"
        private const val COL_PASSWORD = "password"

    }

    //METODO PARA CREAR LA BASE DE DATOS Y SUS TABLAS
    override fun onCreate(db: SQLiteDatabase?) {
        val crearTablaUsuario = """
            CREATE TABLE $TABLE_USUARIO (
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_NOMBRE TEXT NOT NULL,
            $COL_CORREO TEXT NOT NULL UNIQUE,
            $COL_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(crearTablaUsuario)

    }

    //METODO PARA ACTUALIZAR LA BASE DE DATOS
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIO")
        onCreate(db)
    }

    //METODO PARA REGISTRAR UN USUARIO (INSERTAT)
    fun registrarUsuario(nombre : String, correo:String, password:String): Boolean{
        //Crear varible con permisos para escribir en la BD
        val db = writableDatabase

        //CREAR VARIABLE PARA GUARDAR LA INFORMACION
        val datos = contentValuesOf().apply {
            put(COL_NOMBRE,nombre)
            put(COL_CORREO,correo)
            put(COL_PASSWORD,password)

        }

        val resultado = db.insert(TABLE_USUARIO, null,datos)
        return  resultado != -1L


    }

    fun inciarSesion(correo: String, password: String): Boolean{
        val db = readableDatabase

        val query_sql = """
            SELECT * FROM $TABLE_USUARIO
            WHERE $COL_CORREO = ? AND $COL_PASSWORD = ?
        """.trimIndent()

        val cursor = db.rawQuery(
            query_sql, arrayOf(correo, password)
        )
        //contar el numero de resultados encontrados
        val usuarioEncontrado = cursor.count > 0
        cursor.close()
        db.close()
        return usuarioEncontrado

    }




}