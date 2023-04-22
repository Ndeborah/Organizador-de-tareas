package ar.edu.unlam.organizador

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ar.edu.unlam.organizador.databinding.ActivityIngresarAGrupoBinding

class IngresarAGrupoActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityIngresarAGrupoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityIngresarAGrupoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()
    }
}