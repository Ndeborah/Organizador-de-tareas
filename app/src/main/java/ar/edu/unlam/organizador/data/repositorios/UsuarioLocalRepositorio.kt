package ar.edu.unlam.organizador.data.repositorios

interface UsuarioLocalRepositorio {
    fun getIdUsuario(): String?

    fun setIdUsuario(idUsuario: String)

    fun cerrarSesion()
}