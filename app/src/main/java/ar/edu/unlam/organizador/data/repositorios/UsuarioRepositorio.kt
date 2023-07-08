package ar.edu.unlam.organizador.data.repositorios

import ar.edu.unlam.organizador.data.entidades.Grupo
import ar.edu.unlam.organizador.data.entidades.Usuario
import com.google.firebase.database.ValueEventListener

interface UsuarioRepositorio {

    fun getUsuarioByID(id: String, onSucess: (Usuario) -> Unit, onFailure: () -> Unit)

    fun getOrCreate(usuario: Usuario, onSucess: (Usuario) -> Unit, onError: (Exception) -> Unit)

    fun agregar(usuario: Usuario)

    fun agregarGrupo(idUsuario: String, grupo: Grupo, onFailure: () -> Unit)

    fun quitarGrupo(idUsuario: String, idGrupo: String, onFailure: () -> Unit)

    fun listenDb(listener: ValueEventListener)
}
