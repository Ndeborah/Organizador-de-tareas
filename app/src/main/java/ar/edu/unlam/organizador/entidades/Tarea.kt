package ar.edu.unlam.organizador.entidades

import java.time.LocalDate

data class Tarea(
    val nombre: String,
    val fechaInicio: LocalDate,
    val fechaFin: LocalDate,
    val descripcion: String,
    val grupo: Grupo
)
