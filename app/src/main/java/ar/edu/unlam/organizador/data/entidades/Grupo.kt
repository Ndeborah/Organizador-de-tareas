package ar.edu.unlam.organizador.data.entidades

import java.util.UUID

data class Grupo (
  var id: String = UUID.randomUUID().toString(),
  var nombre: String = "",
  var password: String = ""
)
