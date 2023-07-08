package ar.edu.unlam.organizador.data.firebase

import ar.edu.unlam.organizador.data.entidades.Grupo
import ar.edu.unlam.organizador.data.entidades.Usuario
import ar.edu.unlam.organizador.data.repositorios.GrupoRepositorio
import ar.edu.unlam.organizador.data.repositorios.UsuarioRepositorio
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import javax.inject.Inject


class GrupoFirebaseRepo @Inject constructor(val usuarioRepo: UsuarioRepositorio) : GrupoRepositorio {
    private val db = Firebase.database.reference
    private val grupoReference = "grupo"
    private val usuarioReference = "usuario"
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

    override fun listaGruposByIds(idGrupos: List<String>, eventListener: ValueEventListener) {
        db.child(grupoReference).addValueEventListener(eventListener)
    }

    override fun save(grupo: Grupo) {
        db.child(grupoReference).child(grupo.id).setValue(grupo)
    }

    override fun existe(nombre: String): Boolean {
        return listaGrupos.any { grupo: Grupo -> grupo.nombre == nombre }
    }

    fun getById(id: String, onSuccess: (grupo: Grupo) -> Unit, onError: (String) -> Unit) {
        db.child(grupoReference).child(id).get().addOnSuccessListener {
            it.getValue(Grupo::class.java).let { grupo ->
                if (grupo != null) {
                    onSuccess(grupo)
                } else {
                    onError("No se encontró el grupo, f")
                }
            }
        }
    }

    override fun addUsuarioToGrupo(
        idUsuario: String,
        idGrupo: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        db.child(grupoReference).child(idGrupo).get().addOnSuccessListener {
            it.getValue(Grupo::class.java).let { grupo ->
                if (grupo != null) {
                    usuarioRepo.agregarGrupo(idUsuario, grupo) {
                        onError("No se pudo agregar el coso")
                    }
                } else {
                    onError("No se encontró el grupo, f")
                }
            }
        }
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
