package com.example.organizadordetareas.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.organizadordetareas.R
import com.example.organizadordetareas.databinding.ActivityCrearUsuarioBinding
import com.example.organizadordetareas.entidades.Usuario
import com.example.organizadordetareas.repositorios.UsuarioRepositorio

class CrearUsuarioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrearUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCrearUsuarioBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.buttonCrearUsuario2.setOnClickListener {
            if(validarImput()) {
                val nuevoUsuario = Usuario("", "", 0,
                    "", "")

                val nickname = binding.etCrearUsuario.text.toString()
                val password = binding.etCrearPassword.text.toString()
                val confirmacion = binding.etConfirmar.text.toString()
                val codigoDeCuenta = UsuarioRepositorio.usuarios.last().codigoCuenta.plus(1)
                val nombre = binding.etNombre.text.toString()
                val apellido = binding.etApellido.text.toString()

                nuevoUsuario.codigoCuenta = codigoDeCuenta
                nuevoUsuario.nombre = nombre
                nuevoUsuario.apellido = apellido

                if(comprobarSiExisteUsuario(nickname)) {
                    Toast.makeText(this, this.getString(R.string.nicknameExistente), Toast.LENGTH_SHORT).show()
                } else if(!comprobarPasswordIguales(password, confirmacion)) {
                    Toast.makeText(this, this.getString(R.string.passDiferentes), Toast.LENGTH_SHORT).show()
                } else if(!comprobarSeguridadDePassword(nuevoUsuario, password)) {
                    crearDialogBox(this.getString(R.string.passRechazada), this.getString(R.string.passIncorrecta))
                } else {
                    nuevoUsuario.nickname = nickname
                    nuevoUsuario.password = password
                    UsuarioRepositorio.agregar(nuevoUsuario)
                    Toast.makeText(this, this.getString(R.string.usuarioCreado), Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, this.getString(R.string.ingresoFail), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun comprobarSiExisteUsuario(usuario: String): Boolean {
        for(elemento in UsuarioRepositorio.usuarios) {
            if(elemento.nickname == usuario) {
                return true
            }
        }
        return false
    }

    private fun comprobarPasswordIguales(pass: String, confirmacion: String): Boolean {
        return pass == confirmacion
    }

    private fun comprobarSeguridadDePassword(usuario: Usuario, pass: String): Boolean {
        return usuario.comprobarContraseniaAlCrear(pass)
    }

    private fun crearDialogBox(titulo: String, mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(titulo)
        builder.setMessage(mensaje)
        builder.setPositiveButton(this.getString(R.string.aceptar), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun validarImput(): Boolean {
        return binding.etCrearUsuario.text.isNotEmpty() && binding.etCrearPassword.text.isNotEmpty()
                && binding.etConfirmar.text.isNotEmpty() && binding.etNombre.text.isNotEmpty()
                && binding.etApellido.text.isNotEmpty()
    }
}