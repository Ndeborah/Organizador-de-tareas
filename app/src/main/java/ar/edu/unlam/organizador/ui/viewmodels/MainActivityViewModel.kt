package ar.edu.unlam.organizador.ui.viewmodels

import android.content.Context
import android.telephony.PhoneNumberUtils.isGlobalPhoneNumber
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.lang.Exception

data class MainUiState(
    val currentName: String = "",
    val currentNameErrors: MutableList<String> = mutableListOf(),
    val currentTelefono: String = "",
    val currentTelefonoErrors: MutableList<String> = mutableListOf(),
    val tareas: MutableList<Tarea> = mutableListOf(),
    val grupos: MutableList<Grupo> = mutableListOf(),
    val validForm: Boolean = false,
    val loading: Boolean = true
)

data class UsuarioState(
    val usuario: Usuario = Usuario("", ""),
    val error: String = "",
    val hasError: Boolean = false,
    val exists: Boolean = false
)

class MainActivityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private var _usuarioState = MutableStateFlow(UsuarioState())
    val usuarioState = _usuarioState.asStateFlow()

    fun traerTodo() {
        startLoading()
        traerTareas()
        traerGrupos()
        endLoading()
    }

    fun traerTareas() {
        TareaRepositorio.listenDb(listener)
    }

    fun traerGrupos() {
        _uiState.value = _uiState.value.copy(
            grupos = GrupoRepositorio.listaGrupos()
        )
    }

    fun getUsuarioLocal(context: Context) {
        val idUsuarioLocal = UsuarioLocalRepositorio.getIdUsuario(context) ?: return
        startLoading()
        UsuarioRepositorio.getUsuarioByID(idUsuarioLocal,
            onSucess = {
                _usuarioState.value = _usuarioState.value.copy(
                    usuario = it,
                    exists = true
                )
                endLoading()
            },
            onFailure = {
                endLoading()
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

    fun ingresarUsuario(context: Context) {
        UsuarioRepositorio.getOrCreate(
            Usuario(_uiState.value.currentName, _uiState.value.currentTelefono),
            this::storeUserLocally,
            this::onUserGetError
        )
        UsuarioLocalRepositorio.setIdUsuario(context, _uiState.value.currentTelefono)
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

    // Se trae la tarea del repositorio usando el id de la tarea.
    // Le cambia a la tarea el estado de realizado por el opuesto
    // Actualiza la tarea en el repositorio
    fun switchStatusTarea(tareaID: String) {
        TareaRepositorio.get(tareaID) {
            it.realizada = !it.realizada
            TareaRepositorio.update(it)
        }
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