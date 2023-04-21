package com.example.organizadordetareas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.organizadordetareas.databinding.ItemTareaBinding
import com.example.organizadordetareas.entidades.Tarea
import com.example.organizadordetareas.repositorios.TareaRepositorio
import com.example.organizadordetareas.repositorios.UsuarioRepositorio

class TareasAdapter (private val tareas: List<Tarea>) : RecyclerView.Adapter<TareasAdapter.TareasViewHolder>(){

    class TareasViewHolder (val binding: ItemTareaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareasViewHolder {
        val tareaBinding = ItemTareaBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TareasViewHolder(tareaBinding)
    }

    override fun onBindViewHolder(holder: TareasViewHolder, position: Int) {
        val tarea = tareas[position]

        holder.binding.tvCodigoTarea.text = tarea.codigoTarea.toString()
        holder.binding.tvNombreTarea.text = tarea.nombreTarea

        holder.binding.buttonRealizar.setOnClickListener {
            TareaRepositorio.agregarTareaRealizada(tarea)
            TareaRepositorio.tareasPendientes.remove(tarea)
        }
    }

    override fun getItemCount(): Int {
        return tareas.size
    }
}