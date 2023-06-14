package ar.edu.unlam.organizador.database.repositorios

import ar.edu.unlam.organizador.database.entidades.Usuario

object UsuarioRepositorio {
    val usuarios = mutableListOf<Usuario>()


    fun agregar(usuario: Usuario) {
        usuarios.add(usuario)
    }

    fun traerUsuarioLocal(): Usuario? {
        return usuarios.getOrNull(0)
    }

}
