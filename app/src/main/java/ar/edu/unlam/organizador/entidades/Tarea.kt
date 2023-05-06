package ar.edu.unlam.organizador.entidades

import java.time.LocalDate

data class Tarea(
    var nombre: String,
    var fechaInicio: LocalDate,
    var fechaFin: LocalDate,
    var descripcion: String,
    var grupo: Grupo
)
