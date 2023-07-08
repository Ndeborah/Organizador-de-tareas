package ar.edu.unlam.organizador.data.repositorios

import ar.edu.unlam.organizador.data.entidades.Grupo
import com.google.firebase.database.ValueEventListener


interface GrupoRepositorio {
    fun listaDeGrupos(): MutableList<Grupo>
    fun listaGruposByIds(idGrupos: List<String>, eventListener: ValueEventListener)
    fun save(grupo: Grupo)
    fun existe(nombre: String): Boolean
    fun addUsuarioToGrupo(
        idUsuario: String,
        idGrupo: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}
