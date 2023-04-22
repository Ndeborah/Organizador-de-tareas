package ar.edu.unlam.organizador

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ar.edu.unlam.organizador.databinding.ActivityMainBinding
import com.example.organizadordetareas.repositorios.UsuarioRepositorio

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()
        // Botón entrar con usuario anónimo: UsuarioRepositorio.iniciar("Anónimo", "")

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

        binding.buttonAnonimo.setOnClickListener {
            ingresarAnonimo()
        }
    }

    private fun ingresar(usuario: String, password: String) {
        val intent = Intent(this, IngresarAGrupoActivity::class.java).apply {
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

    private fun ingresarAnonimo() {
        ingresar("Anonimo", "")
    }
}
