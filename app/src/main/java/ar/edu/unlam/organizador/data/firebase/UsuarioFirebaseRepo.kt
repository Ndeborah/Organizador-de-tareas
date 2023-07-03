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
    private val firebaseReference = "usuario"

    val usuarios = mutableListOf<Usuario>()

    override fun getUsuarioByID(id: String, onSucess: (Usuario) -> Unit, onFailure: () -> Unit) {
        db.child(firebaseReference).child(id).get().addOnSuccessListener {
            it.getValue(Usuario::class.java).let { usuario ->
                if (usuario != null && usuario.nickname !== "") {
                    onSucess(usuario)
                } else {
                    onFailure()
                }
            }
        }
    }

    override fun getOrCreate(usuario: Usuario, onSucess: (Usuario) -> Unit, onError: (Exception) -> Unit) {
        db.child(firebaseReference).child(usuario.numeroTelefono).get()
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
        db.child(firebaseReference).child(usuario.numeroTelefono).setValue(usuario)
    }

    override fun agregarGrupo(idUsuario: String, grupo: Grupo, onFailure: () -> Unit) {
        db.child(firebaseReference)
            .child(idUsuario)
            .child("grupos")
            .push()
            .setValue(grupo)
            .addOnFailureListener {
                onFailure()
            }
    }


    override fun listenDb(listener: ValueEventListener) {
        db.child(firebaseReference).addValueEventListener(listener)
    }

}
