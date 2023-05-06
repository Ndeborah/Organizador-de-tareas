package ar.edu.unlam.organizador.repositorios

import ar.edu.unlam.organizador.entidades.Grupo

object GrupoRepositorio {
    val grupos = mutableListOf<Grupo>()

    init {
        agregar(Grupo("Grupo 1", 0, ""))
        agregar(Grupo("Grupo 2", 0, ""))
        agregar(Grupo("Grupo 3", 0, ""))
    }

    fun agregar(grupo: Grupo) {
        if (!existe(grupo.nombre)) {
            grupos.add(grupo)
        }
    }

    fun existe(nombre: String): Boolean {
        return grupos.any { grupo: Grupo -> grupo.nombre == nombre }
    }

    fun ingresar(nombre: String): Grupo {
        var grupoIngresado = Grupo()
        for (elemento in grupos) {
            if (elemento.nombre == nombre) {
                grupoIngresado = elemento
            }
        }
        return grupoIngresado
    }
}
