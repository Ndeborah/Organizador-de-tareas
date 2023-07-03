package ar.edu.unlam.organizador.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import ar.edu.unlam.organizador.data.entidades.Usuario
import ar.edu.unlam.organizador.data.repositorios.UsuarioLocalRepositorio
import ar.edu.unlam.organizador.data.repositorios.UsuarioRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


data class UsuarioUiState(
    val usuario: Usuario = Usuario(), val loading: Boolean = true
)

@HiltViewModel
class UsuarioViewModel @Inject constructor(
    private val usuarioLocalRepositorio: UsuarioLocalRepositorio,
    private val usuarioRepositorio: UsuarioRepositorio
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsuarioUiState())
    val uiState: StateFlow<UsuarioUiState> = _uiState.asStateFlow()

    fun cerrarSesion(context: Context) {
        usuarioLocalRepositorio.cerrarSesion(context)
    }

    fun getUsuario(context: Context) {
        startLoading()
        val idUsuarioLocal = usuarioLocalRepositorio.getIdUsuario(context = context) ?: return
        usuarioRepositorio.getUsuarioByID(idUsuarioLocal,
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