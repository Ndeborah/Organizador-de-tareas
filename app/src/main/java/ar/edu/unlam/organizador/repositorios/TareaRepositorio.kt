package ar.edu.unlam.organizador.repositorios

import ar.edu.unlam.organizador.entidades.Tarea

object TareaRepositorio {
    val tareasPendientes = mutableListOf<Tarea>()
    val tareasRealizadas = mutableListOf<Tarea>()

    fun agregarTareaPendiente(tarea: Tarea) {
        tareasPendientes.add(tarea)
    }

    fun agregarTareaRealizada(tarea: Tarea) {
        tareasRealizadas.add(tarea)
    }

/*    fun obtenerListaDeTareasPorUsuario(nickname: String): MutableList<Tarea> {
        val tareasPorUsuario = mutableListOf<Tarea>()
        for (tarea in tareasRealizadas) {
            if (tarea.usuario == nickname) {
                tareasPorUsuario.add(tarea)
            }
        }
        return tareasPorUsuario
    }*/
}