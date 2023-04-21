package com.example.organizadordetareas.repositorios

import com.example.organizadordetareas.entidades.Tarea

object TareaRepositorio {
    val tareasPendientes = mutableListOf<Tarea>()
    val tareasRealizadas = mutableListOf<Tarea>()

    fun agregarTareaPendiente(tarea: Tarea) {
        this.tareasPendientes.add(tarea)
    }

    fun agregarTareaRealizada(tarea: Tarea) {
        this.tareasRealizadas.add(tarea)
    }

    fun obtenerListaDeTareasPorUsuario(nickname: String): MutableList<Tarea> {
        val tareasPorUsuario = mutableListOf<Tarea>()
        for (tarea in tareasRealizadas) {
            if(tarea.usuario == nickname){
                tareasPorUsuario.add(tarea)
            }
        }
        return tareasPorUsuario
    }
}