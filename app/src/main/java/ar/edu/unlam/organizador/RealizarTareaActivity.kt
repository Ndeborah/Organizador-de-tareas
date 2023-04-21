package com.example.organizadordetareas.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organizadordetareas.R
import com.example.organizadordetareas.adapters.TareasRealizadasAdapter
import com.example.organizadordetareas.databinding.ActivityRealizarTareaBinding
import com.example.organizadordetareas.entidades.Usuario
import com.example.organizadordetareas.repositorios.TareaRepositorio
import com.example.organizadordetareas.repositorios.UsuarioRepositorio

class RealizarTareaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRealizarTareaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRealizarTareaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        val bundle = intent.extras
        val username: String? = bundle?.getString("usuario")
        val password: String? = bundle?.getString("password")
        val usuario: Usuario = UsuarioRepositorio.iniciar(username!!, password!!)

        setUpRecyclerView3()
        setUpRecyclerView(usuario)

        binding.tvPorUsuario.text = "${binding.tvPorUsuario.text} ${ usuario.nickname }"

        binding.bottomNavigationView2.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.realizadas -> {
                    irAPendientes(username, password)
                    onPause()
                    true
                }
                else -> false
            }
        }
    }

    private fun setUpRecyclerView3() {
        val recyclerViewTareas = binding.rvTareasRealizadas
        recyclerViewTareas.adapter = TareasRealizadasAdapter(TareaRepositorio.tareasRealizadas)
        recyclerViewTareas.layoutManager = LinearLayoutManager(this)
    }

    private fun setUpRecyclerView(usuario: Usuario) {
        val recyclerViewTareas = binding.rvTareasRealizadasPorUsuario
        recyclerViewTareas.adapter = TareasRealizadasAdapter(TareaRepositorio.obtenerListaDeTareasPorUsuario(usuario.nickname))
        recyclerViewTareas.layoutManager = LinearLayoutManager(this)
    }

    private fun irAPendientes(usuario: String, password: String) {
        val intent = Intent(this, AgregarTareaActivity::class.java).apply {
            putExtra("usuario", usuario)
            putExtra("password", password)
        }
        startActivity(intent)
    }
}