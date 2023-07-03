package ar.edu.unlam.organizador.data.entidades

import java.util.UUID

data class Grupo(
    var id: String = UUID.randomUUID().toString(),
    var nombre: String = "",
    var tareas: MutableMap<String, Tarea> = mutableMapOf()
)

fun Grupo.getTareas() = run { tareas.values }