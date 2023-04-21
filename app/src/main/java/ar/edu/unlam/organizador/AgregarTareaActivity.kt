package com.example.organizadordetareas.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organizadordetareas.R
import com.example.organizadordetareas.adapters.TareasAdapter
import com.example.organizadordetareas.databinding.ActivityAgregarTareaBinding
import com.example.organizadordetareas.entidades.Tarea
import com.example.organizadordetareas.entidades.Usuario
import com.example.organizadordetareas.repositorios.TareaRepositorio
import com.example.organizadordetareas.repositorios.UsuarioRepositorio

class AgregarTareaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgregarTareaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAgregarTareaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        setUpRecyclerView3()

        val bundle = intent.extras
        val username: String? = bundle?.getString("usuario")
        val password: String? = bundle?.getString("password")
        val usuario: Usuario = UsuarioRepositorio.iniciar(username!!, password!!)

        binding.floatingActionButton.setOnClickListener {
            crearTarea()
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.realizadas -> {
                    irARealizadas(username, password)
                    onPause()
                    true
                }
                else -> false
            }
        }
    }

    private fun setUpRecyclerView3() {
        val recyclerViewTareas = binding.rvTareasPendientes
        recyclerViewTareas.adapter = TareasAdapter(TareaRepositorio.tareasPendientes)
        recyclerViewTareas.layoutManager = LinearLayoutManager(this)
    }

    private fun crearTarea() {
        val nuevaTarea = Tarea(0, "","")
        var codigo = if(TareaRepositorio.tareasPendientes.size > 0) TareaRepositorio.tareasPendientes.last().codigoTarea.plus(1) else 0
        TareaRepositorio.agregarTareaPendiente(nuevaTarea)
    }

    private fun irARealizadas(usuario: String, password: String) {
        val intent = Intent(this, RealizarTareaActivity::class.java).apply {
            putExtra("usuario", usuario)
            putExtra("password", password)
        }
        startActivity(intent)
    }
}