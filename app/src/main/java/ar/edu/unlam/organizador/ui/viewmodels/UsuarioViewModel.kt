package ar.edu.unlam.organizador.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import ar.edu.unlam.organizador.data.entidades.Usuario
import ar.edu.unlam.organizador.data.repositorios.UsuarioLocalRepositorio
import ar.edu.unlam.organizador.data.repositorios.UsuarioRepositorio
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


data class UsuarioUiState(
    val usuario: Usuario = Usuario(), val loading: Boolean = true
)

class UsuarioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UsuarioUiState())
    val uiState: StateFlow<UsuarioUiState> = _uiState.asStateFlow()

    fun getUsuario(context: Context) {
        startLoading()
        val idUsuarioLocal = UsuarioLocalRepositorio.getIdUsuario(context = context) ?: return
        UsuarioRepositorio.getUsuarioByID(idUsuarioLocal,
            onSucess = {
                _uiState.value = _uiState.value.copy(
                    usuario = it,
                )
                endLoading()
            },
            onFailure = {
                endLoading()
            }
        )
    }

    private fun startLoading() {
        _uiState.value = _uiState.value.copy(
            loading = true
        )
    }

    private fun endLoading() {
        _uiState.value = _uiState.value.copy(
            loading = false
        )
    }
}