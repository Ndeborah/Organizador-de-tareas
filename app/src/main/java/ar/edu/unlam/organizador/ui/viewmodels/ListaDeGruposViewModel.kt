package ar.edu.unlam.organizador.ui.viewmodels

import androidx.lifecycle.ViewModel
import ar.edu.unlam.organizador.data.entidades.Grupo
import ar.edu.unlam.organizador.data.entidades.Tarea
import ar.edu.unlam.organizador.data.repositorios.GrupoRepositorio
import ar.edu.unlam.organizador.data.repositorios.TareaRepositorio
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ListaUiState(
    val loading: Boolean = false,
    val grupos: MutableList<Grupo> = mutableListOf(),
    val tareas: MutableList<Tarea> = mutableListOf()
)

class ListaDeGruposViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ListaUiState())
    val uiState = _uiState.asStateFlow()


    fun setUp() {
        startLoading()
        TareaRepositorio.listenDb(listener)
        _uiState.value = _uiState.value.copy(
            grupos = GrupoRepositorio.listaGrupos()
        )
        finishLoading()
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