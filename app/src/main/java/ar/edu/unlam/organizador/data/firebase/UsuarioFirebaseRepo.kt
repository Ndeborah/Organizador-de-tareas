package ar.edu.unlam.organizador.data.firebase

import ar.edu.unlam.organizador.data.entidades.Grupo
import ar.edu.unlam.organizador.data.entidades.Usuario
import ar.edu.unlam.organizador.data.repositorios.UsuarioRepositorio
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class UsuarioFirebaseRepo @Inject constructor() : UsuarioRepositorio {

    private val db = Firebase.database.reference
    private val userReference = "usuario"

    val usuarios = mutableListOf<Usuario>()

    override fun getUsuarioByID(id: String, onSucess: (Usuario) -> Unit, onFailure: () -> Unit) {
        db.child(userReference).child(id).get().addOnSuccessListener {
            it.getValue(Usuario::class.java).let { usuario ->
                if (usuario != null && usuario.nickname !== "") {
                    onSucess(usuario)
                } else {
                    onFailure()
                }
            }
        }
    }

    override fun getOrCreate(
        usuario: Usuario,
        onSucess: (Usuario) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.child(userReference).child(usuario.numeroTelefono).get()
            .addOnSuccessListener { item ->
                item.getValue(Usuario::class.java).let {
                    if (it != null) {
                        usuarios.add(it)
                    } else {
                        agregar(usuario)
                    }
                    onSucess(usuario)
                }
            }
            .addOnFailureListener {
                onError(it)
            }
    }

    override fun agregar(usuario: Usuario) {
        db.child(userReference).child(usuario.numeroTelefono).setValue(usuario)
    }

    override fun quitarGrupo(idUsuario: String, idGrupo: String, onFailure: () -> Unit) {
        db.child(userReference)
            .child(idUsuario)
            .child("grupos")
            .child(idGrupo)
            .removeValue()
            .addOnFailureListener {
                onFailure()
            }
    }

    override fun agregarGrupo(idUsuario: String, grupo: Grupo, onFailure: () -> Unit) {
        db.child(userReference)
            .child(idUsuario)
            .child("grupos")
            .child(grupo.id)
            .setValue(grupo)
            .addOnFailureListener {
                onFailure()
            }
    }


    override fun listenDb(listener: ValueEventListener) {
        db.child(userReference).addValueEventListener(listener)
    }

}
