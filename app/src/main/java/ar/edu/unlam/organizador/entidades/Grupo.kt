package ar.edu.unlam.organizador.entidades

data class Grupo (
  val nombre: String,
  val ambiente: Ambientes,
  val cantidadDeParticipantes: Int,
  val contrasenia: String
)
