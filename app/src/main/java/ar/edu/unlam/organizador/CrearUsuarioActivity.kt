package ar.edu.unlam.organizador

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ar.edu.unlam.organizador.databinding.ActivityCrearUsuarioBinding

class CrearUsuarioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrearUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCrearUsuarioBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()


    }
}