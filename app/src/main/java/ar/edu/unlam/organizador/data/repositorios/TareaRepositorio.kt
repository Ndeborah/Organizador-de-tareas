package ar.edu.unlam.organizador.data.repositorios

import ar.edu.unlam.organizador.data.entidades.Tarea
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Singleton

interface TareaRepositorio {
    fun get(grupoId: String, id: String, callback: (Tarea) -> Unit)

    // Se trae una tarea específica y le setea el valor del parámetro tarea.
    fun update(grupoId: String, tarea: Tarea)

    fun deleteByID(grupoId: String, taskId: String)

    // Esta función expone la base de datos a través del event listener
    fun listenDb(listener: ValueEventListener)

    fun save(idGrupo: String, tarea: Tarea)
}