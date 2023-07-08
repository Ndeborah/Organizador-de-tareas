package ar.edu.unlam.organizador.data.device

import android.content.Context
import android.content.SharedPreferences
import ar.edu.unlam.organizador.data.repositorios.UsuarioLocalRepositorio
import javax.inject.Inject

class UsuarioLocalStorageRepo @Inject constructor(
    val context: Context
) : UsuarioLocalRepositorio {

    private val PREFERENCE_NAME = "ORGANIZADOR_TAREAS"
    private val USER_ID = "USER_ID"


    override fun getIdUsuario(): String? {
        val pref: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return pref.getString(USER_ID, "")
    }

    override fun setIdUsuario(idUsuario: String) {
        val pref: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        pref.edit().putString(USER_ID, idUsuario).apply()
    }

    override fun cerrarSesion() {
        val pref: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        pref.edit().remove(USER_ID).apply()
    }
}