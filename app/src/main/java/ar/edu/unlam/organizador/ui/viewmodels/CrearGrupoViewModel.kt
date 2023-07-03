package ar.edu.unlam.organizador.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import ar.edu.unlam.organizador.data.entidades.Grupo
import ar.edu.unlam.organizador.data.entidades.Usuario
import ar.edu.unlam.organizador.data.repositorios.GrupoRepositorio
import ar.edu.unlam.organizador.data.repositorios.UsuarioLocalRepositorio
import ar.edu.unlam.organizador.data.repositorios.UsuarioRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class UiState(
    val usuario: Usuario = Usuario("", ""),
    val loading: Boolean = true,
    val hasError: Boolean = false,
    var errorMessage: String = ""
)

@HiltViewModel
class CrearGrupoViewModel @Inject constructor(
    private val usuarioLocalRepositorio: UsuarioLocalRepositorio,
    private val grupoRepositorio: GrupoRepositorio,
    private val usuarioRepositorio: UsuarioRepositorio
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun getUsuarioLocal(context: Context) {
        val idUsuarioLocal = usuarioLocalRepositorio.getIdUsuario(context) ?: return
        startLoading()
        usuarioRepositorio.getUsuarioByID(idUsuarioLocal,
            onSucess = {
                _uiState.value = _uiState.value.copy(
                    usuario = it
                )
                finishLoading()
            },
            onFailure = {
                finishLoading()
            }
        )
    }

    fun crearGrupo(nombre: String, idUsuario: String) {
        val grupo = Grupo(nombre = nombre)
        grupoRepositorio.save(grupo)
        usuarioRepositorio.agregarGrupo(idUsuario, grupo) {
            _uiState.value = _uiState.value.copy(
                hasError = true,
                errorMessage = "Error al dar de alta un grupo 🥹"
            )
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