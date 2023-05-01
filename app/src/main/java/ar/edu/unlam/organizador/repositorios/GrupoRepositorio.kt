package ar.edu.unlam.organizador.repositorios

import ar.edu.unlam.organizador.entidades.Grupo

object GrupoRepositorio {
    val grupos = mutableListOf<Grupo>()

    fun agregar(grupo: Grupo) {
        if (!existe(grupo.nombre)) {
            grupos.add(grupo)
        }
    }

    fun existe(nombre: String): Boolean {
        return grupos.any { grupo: Grupo -> grupo.nombre == nombre }
    }
}
