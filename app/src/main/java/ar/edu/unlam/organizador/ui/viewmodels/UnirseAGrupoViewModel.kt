package ar.edu.unlam.organizador.ui.viewmodels

import androidx.lifecycle.ViewModel
import ar.edu.unlam.organizador.data.repositorios.GrupoRepositorio
import ar.edu.unlam.organizador.data.repositorios.UsuarioLocalRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


data class UnirseAGrupoFormState(
    val currentId: String = "",
    val validForm: Boolean = false,
    val currentIdErrors: MutableList<String> = mutableListOf(),
)

@HiltViewModel
class UnirseAGrupoViewModel @Inject constructor(
    val grupoRepositorio: GrupoRepositorio,
    val usuarioLocalRepositorio: UsuarioLocalRepositorio
) : ViewModel() {
    private val _formState = MutableStateFlow(UnirseAGrupoFormState())
    val formState = _formState.asStateFlow()

    fun agregarGrupo(onSuccess: () -> Unit, onError: (String) -> Unit) {
        grupoRepositorio.addUsuarioToGrupo(
            idGrupo = _formState.value.currentId,
            idUsuario = usuarioLocalRepositorio.getIdUsuario()!!,
            onError = onError,
            onSuccess = onSuccess
        )
    }

    fun validarId(id: String) {
        val errors = mutableListOf<String>()
        if (id.length < 4) {
            errors.add("Ingrese al menos cuatro caracteres 😳.")
        }
        _formState.value = _formState.value.copy(
            currentId = id,
            currentIdErrors = errors
        )
        validarFormulario()
    }

    fun validarFormulario() {
        if (_formState.value.currentIdErrors.size < 1) {
            _formState.value = _formState.value.copy(
                validForm = true
            )
        } else {
            _formState.value = _formState.value.copy(
                validForm = false
            )
        }
    }
}