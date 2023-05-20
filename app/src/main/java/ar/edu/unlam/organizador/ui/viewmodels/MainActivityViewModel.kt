package ar.edu.unlam.organizador.ui.viewmodels

import android.telephony.PhoneNumberUtils.isGlobalPhoneNumber
import androidx.lifecycle.ViewModel
import ar.edu.unlam.organizador.entidades.Usuario
import ar.edu.unlam.organizador.repositorios.UsuarioRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MainUiState(
    val currentName: String = "",
    val currentNameErrors: MutableList<String> = mutableListOf(),
    val currentTelefono: String = "",
    val currentTelefonoErrors: MutableList<String> = mutableListOf(),
)


class MainActivityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun actualizarNombre(nombre: String) {
        val errors = mutableListOf<String>()
        if (nombre.length < 4) {
            errors.add("El nombre debe de tener al menos cuatro caracteres 😳.")
        }
        _uiState.value = _uiState.value.copy(
            currentName = nombre,
            currentNameErrors = errors
        )
    }

    fun actualizarTelefono(telefono: String) {
        val errors = mutableListOf<String>()
        if (!isGlobalPhoneNumber(telefono)) {
            errors.add("Debe ser un número de teléfono válido 😳.")
        }
        _uiState.value = _uiState.value.copy(
            currentTelefono = telefono,
            currentTelefonoErrors = errors
        )
    }

    fun crearUsuario() {
        UsuarioRepositorio.agregar(
            Usuario(_uiState.value.currentName, _uiState.value.currentTelefono)
        )
    }

}