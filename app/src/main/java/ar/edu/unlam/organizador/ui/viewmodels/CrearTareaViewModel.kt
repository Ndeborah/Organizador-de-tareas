package ar.edu.unlam.organizador.ui.viewmodels

import androidx.lifecycle.ViewModel
import ar.edu.unlam.organizador.data.entidades.Tarea
import ar.edu.unlam.organizador.data.repositorios.TareaRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CrearTareaViewModel @Inject constructor(
    private val tareaRepositorio: TareaRepositorio
) : ViewModel() {

    fun save(idGrupo: String, nuevaTarea: Tarea) {
        tareaRepositorio.save(idGrupo, nuevaTarea)
    }
}