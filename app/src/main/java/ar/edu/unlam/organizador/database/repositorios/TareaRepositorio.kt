package ar.edu.unlam.organizador.database.repositorios

import ar.edu.unlam.organizador.GrupoActivity
import ar.edu.unlam.organizador.database.entidades.Tarea
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

object TareaRepositorio {

    private val db = Firebase.database.reference
    private const val tareaReference = "tarea"
    val tareasPendientes = mutableListOf<Tarea>()
    val tareasRealizadas = mutableListOf<Tarea>()

    init {
        listaTareasPendientes()
        listaTareasRealizadas()
    }

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

    fun listaTareasPendientes(): MutableList<Tarea> {
        val listener = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { child ->
                    val tarea = Tarea(
                        child.child("id").getValue<String>()!!,
                        child.child("nombre").getValue<String>()!!,
                        child.child("grupo").getValue<String>()!!,
                        child.child("realizada").getValue<Boolean>()!!
                    )
                    tarea.let {
                        if(!existe(it.nombre) && !it.realizada) {
                            tareasPendientes.add(it)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        db.child(tareaReference).addValueEventListener(listener)
        return tareasPendientes
    }

    fun listaTareasRealizadas(): MutableList<Tarea> {
        val listener = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { child ->
                    val tarea = Tarea(
                        child.child("id").getValue<String>()!!,
                        child.child("nombre").getValue<String>()!!,
                        child.child("grupo").getValue<String>()!!,
                        child.child("realizada").getValue<Boolean>()!!
                    )
                    tarea.let {
                        if(!existe(it.nombre) && it.realizada) {
                            tareasPendientes.add(it)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        db.child(tareaReference).addValueEventListener(listener)
        return tareasRealizadas
    }

    fun existe(nombre: String): Boolean {
        return tareasPendientes.any { tarea: Tarea -> tarea.nombre == nombre }
    }

    fun save(tarea: Tarea) {
        db.child(tareaReference).child(tarea.id).setValue(tarea)
    }

    fun obtenerListaDeTareasRealizadasPorGrupo(nombre: String): MutableList<Tarea> {
        listaTareasRealizadas()
        val tareasPorUsuario = mutableListOf<Tarea>()
        for (tarea in listaTareasRealizadas()) {
            if (tarea.grupo == nombre) {
                tareasPorUsuario.add(tarea)
            }
        }
        return tareasPorUsuario
    }

    fun obtenerListaDeTareasPendientesPorGrupo(nombre: String): MutableList<Tarea> {
        listaTareasPendientes()
        val tareasPorUsuario = mutableListOf<Tarea>()
        for (tarea in listaTareasPendientes()) {
            if (tarea.grupo == nombre) {
                tareasPorUsuario.add(tarea)
            }
        }
        return tareasPorUsuario
    }
}