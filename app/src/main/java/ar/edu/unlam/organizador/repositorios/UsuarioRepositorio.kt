package ar.edu.unlam.organizador.repositorios

import ar.edu.unlam.organizador.entidades.Usuario

object UsuarioRepositorio {
    val usuarios = mutableListOf<Usuario>()


    fun agregar(usuario: Usuario) {
        usuarios.add(usuario)
    }

    fun traerUsuarioLocal(): Usuario? {
        return usuarios.getOrNull(0)
    }

}
