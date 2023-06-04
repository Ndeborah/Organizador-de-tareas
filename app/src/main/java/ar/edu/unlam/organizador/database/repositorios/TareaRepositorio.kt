package ar.edu.unlam.organizador.database.repositorios

import ar.edu.unlam.organizador.database.entidades.Grupo
import ar.edu.unlam.organizador.database.entidades.Tarea
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
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

    fun listenGetAll(callbackAdd: (Tarea) -> Unit, callbackChange: (Tarea) -> Unit) {
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                dataSnapshot.getValue(Tarea::class.java)?.let(callbackAdd)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                dataSnapshot.getValue(Tarea::class.java)?.let(callbackChange)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        db.child(tareaReference).addChildEventListener(childEventListener)
    }

    fun save(tarea: Tarea) {
        db.child(tareaReference).child(tarea.id).setValue(tarea)
    }



    /*val tareasPendientes = mutableListOf<Tarea>()
    val tareasRealizadas = mutableListOf<Tarea>()

    /*init {
        agregarTareaPendiente(Tarea("Tarea 1", "Grupo 1"))
        agregarTareaPendiente(Tarea("Tarea 2", "Grupo 1"))
        agregarTareaPendiente(Tarea("Tarea 3", "Grupo 1"))
        agregarTareaPendiente(Tarea("Tarea 1", "Grupo 2"))
        agregarTareaPendiente(Tarea("Tarea 1", "Grupo 3"))
        agregarTareaRealizada(Tarea("Tarea 3", "Grupo 2"))
        agregarTareaRealizada(Tarea("Tarea 2", "Grupo 3"))
    }*/

    fun listarTareasPendietes(): MutableList<Tarea> {
        return tareasPendientes
    }

    fun listarTareasRealizadas(): MutableList<Tarea> {
        return tareasRealizadas
    }

    fun agregarTareaPendiente(tarea: Tarea) {
        tareasPendientes.add(tarea)
    }

    fun agregarTareaRealizada(tarea: Tarea) {
        tareasRealizadas.add(tarea)
    }

    fun obtenerListaDeTareasRealizadasPorGrupo(nombre: String): MutableList<Tarea> {
        val tareasPorUsuario = mutableListOf<Tarea>()
        for (tarea in tareasRealizadas) {
            if (tarea.grupo == nombre) {
                tareasPorUsuario.add(tarea)
            }
        }
        return tareasPorUsuario
    }

    fun obtenerListaDeTareasPendientesPorGrupo(nombre: String): MutableList<Tarea> {
        val tareasPorUsuario = mutableListOf<Tarea>()
        for (tarea in tareasPendientes) {
            if (tarea.grupo == nombre) {
                tareasPorUsuario.add(tarea)
            }
        }
        return tareasPorUsuario
    }*/
}