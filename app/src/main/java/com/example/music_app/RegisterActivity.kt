package com.example.music_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNombre : EditText
    private lateinit var etCorreo : EditText
    private lateinit var etPassword : EditText
    private lateinit var btnRegistrer : Button

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        etNombre = findViewById(R.id.etNombre)
        etCorreo = findViewById(R.id.etCorreo)
        etPassword = findViewById(R.id.etPassword)
        btnRegistrer = findViewById(R.id.btnRegistrar)

        dbHelper = DatabaseHelper(this)

        //crear elemento para registrar un usuario
        btnRegistrer.setOnClickListener {
            registrarUsuario()
        }

    }

    //METODO PARA REGISTRAR UN USUARIO
    private fun registrarUsuario(){
        //Obtener la informacion que escribe el usuario
        var nombre = etNombre.text.toString().trim()
        val correo = etCorreo.text.toString().trim()
        val pass = etPassword.text.toString().trim()

        //VALIDAR QUE EL USUARIO LLENE LOS CAMPOS
        if(nombre.isEmpty() || correo.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val resultado = dbHelper.registrarUsuario(nombre ,correo ,pass)

        if (resultado) {
            //si resultado es verdadero
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()

        }
    }

}
