package ar.edu.unlam.organizador.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import ar.edu.unlam.organizador.data.entidades.Grupo
import ar.edu.unlam.organizador.data.entidades.Tarea
import ar.edu.unlam.organizador.data.entidades.Usuario
import ar.edu.unlam.organizador.data.repositorios.GrupoRepositorio
import ar.edu.unlam.organizador.data.repositorios.TareaRepositorio
import ar.edu.unlam.organizador.data.repositorios.UsuarioLocalRepositorio
import ar.edu.unlam.organizador.data.repositorios.UsuarioRepositorio
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ListaDeGruposUIState(
    val loading: Boolean = false,
    val grupos: MutableCollection<Grupo> = mutableListOf(),
    val usuario: Usuario = Usuario("", ""),
    val tareas: MutableList<Tarea> = mutableListOf()
)

@HiltViewModel
class ListaDeGruposViewModel @Inject constructor(
    private val grupoRepositorio: GrupoRepositorio,
    private val usuarioLocalRepositorio: UsuarioLocalRepositorio,
    private val usuarioRepositorio: UsuarioRepositorio

) : ViewModel() {
    private val _uiState = MutableStateFlow(ListaDeGruposUIState())
    val uiState = _uiState.asStateFlow()

    fun setUp() {
        startLoading()
        getUsuarioLocal()
        finishLoading()
    }

    fun getUsuarioLocal() {
        val idUsuarioLocal = usuarioLocalRepositorio.getIdUsuario() ?: return
        usuarioRepositorio.getUsuarioByID(idUsuarioLocal,
            onSucess = {
                traerGrupos(it)
            },
            onFailure = {
                throw Exception("lele")
            }
        )
    }

    fun traerGrupos(usuario: Usuario) {
        val idGrupos = mutableListOf<String>()
        usuario.grupos.forEach {
            idGrupos.add(it.value.id)
        }
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                _uiState.value = _uiState.value.copy(
                    grupos = dataSnapshot.getValue<HashMap<String, Grupo>>()!!.values.filter { grupo ->
                        idGrupos.contains(grupo.id)
                    }.toMutableList()
                )
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        grupoRepositorio.listaGruposByIds(idGrupos, listener)
    }

    // Crea un listener para escuchar los cambios que hay en la base de datos de firebase
    // Se trae la lista y la mapea a una lista de tareas
    // Finalmente actualiza el valor del live data de tareas con el valor mapeado antes
    private val listener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                _uiState.value = _uiState.value.copy(
                    tareas = dataSnapshot.children
                        .mapNotNull { child -> child.getValue(Tarea::class.java) }
                        .toMutableList()
                )
            }
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }

    private fun startLoading() {
        _uiState.value = _uiState.value.copy(
            loading = true
        )
    }

    private fun finishLoading() {
        _uiState.value = _uiState.value.copy(
            loading = false
        )
    }
}