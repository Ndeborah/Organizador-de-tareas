package com.example.organizadordetareas.repositorios

import com.example.organizadordetareas.entidades.Usuario

object UsuarioRepositorio {
    val usuarios = mutableListOf<Usuario>()

    fun agregar(usuario: Usuario) {
        if (existe(usuario.nickname, usuario.password)) println("Error al agregar la cuenta.")
        else usuarios.add(usuario)
    }

    fun existe(nickname: String, password: String): Boolean {
        return (usuarios.any { usuario: Usuario -> usuario.nickname == nickname && usuario.password == password });
    }

    fun iniciar(nickname: String, password: String): Usuario {
        var usuarioIniciado = Usuario()
        for (elemento in usuarios) {
            if (elemento.nickname == nickname && elemento.password == password) {
                usuarioIniciado = elemento
            }
        }
        return usuarioIniciado
    }
}