package com.example.organizadordetareas.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.organizadordetareas.R
import com.example.organizadordetareas.databinding.ActivityMainBinding
import com.example.organizadordetareas.repositorios.UsuarioRepositorio

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.buttonIngresar.setOnClickListener {
            val usuario = binding.etUsuario.text.toString()
            val password = binding.etPassword.text.toString()

            if(validarImput()) {
                if(UsuarioRepositorio.existe(usuario, password)) {
                    ingresar(usuario, password)
                    finish()
                } else {
                    Toast.makeText(this, this.getString(R.string.datosIncorrectos), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, this.getString(R.string.ingresoFail), Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonCrearUsuario.setOnClickListener {
            crearUsuario()
            onPause()
        }
    }

    private fun ingresar(usuario: String, password: String) {
        val intent = Intent(this, AgregarTareaActivity::class.java).apply {
            putExtra("usuario", usuario)
            putExtra("password", password)
        }
        startActivity(intent)
    }

    private fun crearUsuario() {
        val intent = Intent(this, CrearUsuarioActivity::class.java)
        startActivity(intent)
    }

    private fun validarImput(): Boolean {
        return binding.etUsuario.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()
    }
}