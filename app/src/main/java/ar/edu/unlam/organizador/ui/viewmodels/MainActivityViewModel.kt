package ar.edu.unlam.organizador.ui.viewmodels

import android.telephony.PhoneNumberUtils.isGlobalPhoneNumber
import androidx.lifecycle.ViewModel
import ar.edu.unlam.organizador.database.entidades.Usuario
import ar.edu.unlam.organizador.database.repositorios.UsuarioRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MainUiState(
    val currentName: String = "",
    val currentNameErrors: MutableList<String> = mutableListOf(),
    val currentTelefono: String = "",
    val currentTelefonoErrors: MutableList<String> = mutableListOf(),
    val validForm: Boolean = false
)


class MainActivityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun validarFormulario() {
        if (this._uiState.value.currentNameErrors.size == 0
            && this._uiState.value.currentTelefonoErrors.size == 0
            && this._uiState.value.currentName != ""
            && this._uiState.value.currentTelefono != ""
        ) {
            _uiState.value = this._uiState.value.copy(
                validForm = true
            )
        } else {
            _uiState.value = this._uiState.value.copy(
                validForm = false
            )
        }
    }

    fun actualizarNombre(nombre: String) {
        val errors = mutableListOf<String>()
        if (nombre.length < 4) {
            errors.add("Ingrese al menos cuatro caracteres 😳.")
        }
        _uiState.value = _uiState.value.copy(
            currentName = nombre,
            currentNameErrors = errors
        )
        validarFormulario()
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
        validarFormulario()
    }

    fun crearUsuario() {
        UsuarioRepositorio.agregar(
            Usuario(_uiState.value.currentName, _uiState.value.currentTelefono)
        )
    }

}