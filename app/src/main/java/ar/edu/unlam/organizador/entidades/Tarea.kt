package com.example.organizadordetareas.entidades

import java.time.LocalDate
import java.time.LocalTime

data class Tarea (
    val nombre: String,
    val fechaInicio: LocalDate,
    val fechaFin: LocalDate,
    val descripcion: String,
    val grupo: Grupo
)
