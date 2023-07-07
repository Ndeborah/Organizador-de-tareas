package ar.edu.unlam.organizador.ui.viewmodels

import android.content.Context
import android.telephony.PhoneNumberUtils.isGlobalPhoneNumber
import androidx.lifecycle.ViewModel
import ar.edu.unlam.organizador.data.entidades.Grupo
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
import java.lang.Exception
import javax.inject.Inject

data class MainUiState(
    val currentName: String = "",
    val currentNameErrors: MutableList<String> = mutableListOf(),
    val currentTelefono: String = "",
    val currentTelefonoErrors: MutableList<String> = mutableListOf(),
    val grupos: List<Grupo> = mutableListOf(),
    val validForm: Boolean = false,
    val loading: Boolean = true
)

data class UsuarioState(
    val usuario: Usuario = Usuario("", ""),
    val error: String = "",
    val hasError: Boolean = false,
    val exists: Boolean = false
)

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val grupoRepositorio: GrupoRepositorio,
    private val usuarioLocalRepositorio: UsuarioLocalRepositorio,
    private val usuarioRemotoRepositorio: UsuarioRepositorio,
    private val tareaRepositorio: TareaRepositorio
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    private var _usuarioState = MutableStateFlow(UsuarioState())
    val usuarioState = _usuarioState.asStateFlow()

    fun traerTodo() {
        startLoading()
        traerGrupos()
        finishLoading()
    }

    fun traerGrupos() {
        val idGrupos = mutableListOf<String>()
        _usuarioState.value.usuario.grupos.forEach {
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

    fun getUsuarioLocal() {
        val idUsuarioLocal = usuarioLocalRepositorio.getIdUsuario() ?: return
        startLoading()
        usuarioRemotoRepositorio.getUsuarioByID(idUsuarioLocal,
            onSucess = {
                _usuarioState.value = _usuarioState.value.copy(
                    usuario = it,
                    exists = true
                )
                finishLoading()
            },
            onFailure = {
                finishLoading()
            }
        )
    }

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

    fun borrarTareaById(idTarea: String) {
        tareaRepositorio.deleteByID(idTarea)
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

    private fun storeUserLocally(usuario: Usuario) {
        _usuarioState.value = _usuarioState.value.copy(
            exists = true,
            usuario = usuario,
            error = ""
        )
    }

    private fun onUserGetError(exception: Exception) {
        var errorMessage = ""

        exception.message?.let {
            errorMessage = it
        }

        _usuarioState.value = _usuarioState.value.copy(
            error = errorMessage,
            hasError = true
        )
    }

    fun ingresarUsuario() {
        usuarioRemotoRepositorio.getOrCreate(
            Usuario(_uiState.value.currentName, _uiState.value.currentTelefono),
            this::storeUserLocally,
            this::onUserGetError
        )
        usuarioLocalRepositorio.setIdUsuario(_uiState.value.currentTelefono)
    }

    // Se trae la tarea del repositorio usando el id de la tarea.
    // Le cambia a la tarea el estado de realizado por el opuesto
    // Actualiza la tarea en el repositorio
    fun switchStatusTarea(grupoId: String, tareaId: String) {
        tareaRepositorio.get(grupoId, tareaId) {
            it.realizada = !it.realizada
            tareaRepositorio.update(grupoId, it)
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