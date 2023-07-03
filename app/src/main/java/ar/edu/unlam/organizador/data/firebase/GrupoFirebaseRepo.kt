package ar.edu.unlam.organizador.data.firebase

import ar.edu.unlam.organizador.data.entidades.Grupo
import ar.edu.unlam.organizador.data.repositorios.GrupoRepositorio
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import javax.inject.Inject


class GrupoFirebaseRepo @Inject constructor() : GrupoRepositorio {
    private val db = Firebase.database.reference
    private val grupoReference = "grupo"
    val listaGrupos: MutableList<Grupo> = mutableListOf()

    override fun listaDeGrupos(): MutableList<Grupo> {
        return listaGrupos
    }


    fun listenGetAll(callbackAdd: (Grupo) -> Unit, callbackChange: (Grupo) -> Unit) {
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                dataSnapshot.getValue(Grupo::class.java)?.let(callbackAdd)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                dataSnapshot.getValue(Grupo::class.java)?.let(callbackChange)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }
        db.child(grupoReference).addChildEventListener(childEventListener)
    }

    fun listaGrupos(): MutableList<Grupo> {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { child ->
                    val grupo = Grupo(
                        child.child("id").getValue<String>()!!,
                        child.child("nombre").getValue<String>()!!,
                    )
                    grupo.let {
                        if (!existe(it.nombre)) {
                            listaGrupos.add(it)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        db.child(grupoReference).addValueEventListener(listener)
        return listaGrupos
    }

    override fun listaGruposByIds(idGrupos: List<String>, eventListener: ValueEventListener) {
        db.child(grupoReference).addValueEventListener(eventListener)
    }

    override fun save(grupo: Grupo) {
        db.child(grupoReference).child(grupo.id).setValue(grupo)
    }

    override fun existe(nombre: String): Boolean {
        return listaGrupos.any { grupo: Grupo -> grupo.nombre == nombre }
    }

    /*fun buscarGrupo(nombre: String): Grupo {
        var grupoEncontrado = Grupo()
        for (elemento in listaGrupos) {
            if (elemento.nombre == nombre) {
                grupoEncontrado = elemento
            }
        }
        return grupoEncontrado
    }*/
}
