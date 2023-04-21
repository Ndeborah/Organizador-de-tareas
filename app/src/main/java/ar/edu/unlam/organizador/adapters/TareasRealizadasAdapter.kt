package com.example.organizadordetareas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.organizadordetareas.databinding.ItemTareaRealizadaBinding
import com.example.organizadordetareas.entidades.Tarea

class TareasRealizadasAdapter (private val tareas: List<Tarea>) : RecyclerView.Adapter<TareasRealizadasAdapter.TareasRealizadasViewHolder>(){

    class TareasRealizadasViewHolder (val binding: ItemTareaRealizadaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareasRealizadasViewHolder {
        val tareaBinding = ItemTareaRealizadaBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TareasRealizadasViewHolder(tareaBinding)
    }

    override fun onBindViewHolder(holder: TareasRealizadasViewHolder, position: Int) {
        val tarea = tareas[position]

        holder.binding.tvCodigoTarea.text = tarea.codigoTarea.toString()
        holder.binding.tvNombreTarea.text = tarea.nombreTarea
        holder.binding.tvUsuario3.text = tarea.usuario
    }

    override fun getItemCount(): Int {
        return tareas.size
    }
}