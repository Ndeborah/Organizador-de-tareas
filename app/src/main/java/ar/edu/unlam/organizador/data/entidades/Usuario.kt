package ar.edu.unlam.organizador.data.entidades

data class Usuario(
    var nickname: String = "",
    var numeroTelefono: String = "",
    var grupos: HashMap<String, Grupo> = HashMap()
)
