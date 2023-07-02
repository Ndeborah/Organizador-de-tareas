package ar.edu.unlam.organizador.data.repositorios

import ar.edu.unlam.organizador.data.entidades.Usuario
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

object UsuarioRepositorio {

    private val db = Firebase.database.reference
    private const val firebaseReference = "usuario"

    val usuarios = mutableListOf<Usuario>()

    private fun onUserExists(usuario: Usuario) {
        usuarios.add(usuario)
    }

    fun agregar(usuario: Usuario) {
        db.child(firebaseReference).child(usuario.numeroTelefono).setValue(usuario)
    }

    fun getUsuarioByID(id: String, onSucess: (Usuario) -> Unit, onFailure: () -> Unit) {
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

    fun getOrCreate(usuario: Usuario, onSucess: (Usuario) -> Unit, onError: (Exception) -> Unit) {
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


    fun listenDb(listener: ValueEventListener) {
        db.child(firebaseReference).addValueEventListener(listener)
    }

}
