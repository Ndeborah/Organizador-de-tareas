package com.example.organizadordetareas.entidades

import java.time.LocalDate
import java.time.LocalTime

data class Tarea (
    val codigoTarea: Int,
    val nombreTarea: String,
    /*val fecha: LocalDate,
    val hora: LocalTime,*/
    val usuario: String
)