package ar.edu.unlam.organizador.database.entidades

data class Tarea(
    var id: String,
    var nombre: String,
    var grupo: String,
    var realizada: Boolean
)
