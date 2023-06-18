package ar.edu.unlam.organizador.database.repositorios

import ar.edu.unlam.organizador.database.entidades.Tarea
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object TareaRepositorio {

    private val db = Firebase.database.reference
    private const val tareaReference = "tarea"

    fun get(id: String, callback: (Tarea) -> Unit) {
        db.child(tareaReference).child(id).get().addOnSuccessListener { item ->
            item.getValue(Tarea::class.java)?.let {
                callback(it)
            }
        }
    }

    // Se trae una tarea específica y le setea el valor del parámetro tarea.
    fun update(tarea: Tarea) {
        db.child(tareaReference).child(tarea.id).setValue(tarea)
    }

    fun deleteByID(taskId: String) {
        db.child(tareaReference).child(taskId).removeValue()
    }

    // Esta función expone la base de datos a través del event listener
    fun listenDb(listener: ValueEventListener) {
        db.child(tareaReference).addValueEventListener(listener)
    }

    fun save(tarea: Tarea) {
        db.child(tareaReference).child(tarea.id).setValue(tarea)
    }
}