package ar.edu.unlam.organizador.database.entidades

import java.util.UUID

data class Tarea (
    var id: String = UUID.randomUUID().toString(),
    var nombre: String = "",
    var grupo: String = "",
    var realizada: Boolean = false
)
