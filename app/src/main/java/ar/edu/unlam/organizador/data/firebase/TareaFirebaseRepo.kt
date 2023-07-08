package ar.edu.unlam.organizador.data.firebase

import ar.edu.unlam.organizador.data.entidades.Tarea
import ar.edu.unlam.organizador.data.repositorios.TareaRepositorio
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class TareaFirebaseRepo @Inject constructor() : TareaRepositorio {

    private val db = Firebase.database.reference
    private val grupoReference = "grupo"
    private val tareaReference = "tareas"

    override fun get(grupoId: String, id: String, callback: (Tarea) -> Unit) {
        db.child(grupoReference)
            .child(grupoId)
            .child(tareaReference).child(id).get().addOnSuccessListener { item ->
                item.getValue(Tarea::class.java)?.let {
                    callback(it)
                }
            }
    }

    // Se trae una tarea específica y le setea el valor del parámetro tarea.
    override fun update(grupoId: String, tarea: Tarea) {
        db.child(grupoReference)
            .child(grupoId)
            .child(tareaReference)
            .child(tarea.id).setValue(tarea)
    }

    override fun deleteByID(grupoId: String, taskId: String) {
        db.child(grupoReference)
            .child(grupoId)
            .child(tareaReference).child(taskId).removeValue()
    }

    // Esta función expone la base de datos a través del event listener
    override fun listenDb(listener: ValueEventListener) {
        db.child(tareaReference).addValueEventListener(listener)
    }

    override fun save(idGrupo: String, tarea: Tarea) {
        db.child(grupoReference)
            .child(idGrupo)
            .child(tareaReference)
            .child(tarea.id)
            .setValue(tarea)
    }
}