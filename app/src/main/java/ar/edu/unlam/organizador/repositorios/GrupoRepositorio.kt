package ar.edu.unlam.organizador.repositorios

import ar.edu.unlam.organizador.entidades.Grupo
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object GrupoRepositorio {

    val grupos = mutableListOf<Grupo>()

    init {
        agregar(Grupo("Grupo 1", ""))
        agregar(Grupo("Grupo 2", ""))
        agregar(Grupo("Grupo 3", ""))
    }

    fun agregar(grupo: Grupo) {
        if (!existe(grupo.nombre)) {
            grupos.add(grupo)
        }
    }

    fun existe(nombre: String): Boolean {
        return grupos.any { grupo: Grupo -> grupo.nombre == nombre }
    }

    fun buscarGrupo(nombre: String): Grupo {
        var grupoEncontrado = Grupo()
        for (elemento in grupos) {
            if (elemento.nombre == nombre) {
                grupoEncontrado = elemento
            }
        }

        return grupoEncontrado
    }
}
