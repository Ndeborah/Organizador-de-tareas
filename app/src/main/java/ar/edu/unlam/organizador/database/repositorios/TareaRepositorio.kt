package ar.edu.unlam.organizador.database.repositorios

import ar.edu.unlam.organizador.database.entidades.Tarea

object TareaRepositorio {
    val tareasPendientes = mutableListOf<Tarea>()
    val tareasRealizadas = mutableListOf<Tarea>()

    /*init {
        agregarTareaPendiente(Tarea("Tarea 1", "Grupo 1"))
        agregarTareaPendiente(Tarea("Tarea 2", "Grupo 1"))
        agregarTareaPendiente(Tarea("Tarea 3", "Grupo 1"))
        agregarTareaPendiente(Tarea("Tarea 1", "Grupo 2"))
        agregarTareaPendiente(Tarea("Tarea 1", "Grupo 3"))
        agregarTareaRealizada(Tarea("Tarea 3", "Grupo 2"))
        agregarTareaRealizada(Tarea("Tarea 2", "Grupo 3"))
    }*/

    fun listarTareasPendietes(): MutableList<Tarea> {
        return tareasPendientes
    }

    fun listarTareasRealizadas(): MutableList<Tarea> {
        return tareasRealizadas
    }

    fun agregarTareaPendiente(tarea: Tarea) {
        tareasPendientes.add(tarea)
    }

    fun agregarTareaRealizada(tarea: Tarea) {
        tareasRealizadas.add(tarea)
    }

    fun obtenerListaDeTareasRealizadasPorGrupo(nombre: String): MutableList<Tarea> {
        val tareasPorUsuario = mutableListOf<Tarea>()
        for (tarea in tareasRealizadas) {
            if (tarea.grupo == nombre) {
                tareasPorUsuario.add(tarea)
            }
        }
        return tareasPorUsuario
    }

    fun obtenerListaDeTareasPendientesPorGrupo(nombre: String): MutableList<Tarea> {
        val tareasPorUsuario = mutableListOf<Tarea>()
        for (tarea in tareasPendientes) {
            if (tarea.grupo == nombre) {
                tareasPorUsuario.add(tarea)
            }
        }
        return tareasPorUsuario
    }
}