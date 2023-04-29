package com.example.organizadordetareas.repositorios

object GrupoRepositorio {
  val grupos = mutableListOf<Grupo>()
  
  fun agregar(grupo: Grupo): Boolean {
        return if (existe(grupo.nombre) {
            false
        } else {
            grupos.add(grupo)
            true
        }
   }

   fun existe(nombre: String): Boolean {
       return grupos.any { grupo: Grupo -> grupo.nombre == nombre }
   }
}
