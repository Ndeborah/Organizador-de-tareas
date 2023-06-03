package ar.edu.unlam.organizador.database.entidades

import java.util.UUID

data class Grupo (
  var id: String = UUID.randomUUID().toString(),
  val nombre: String = "",
  var contrasenia: String = ""
)
