package ar.edu.unlam.organizador.data.repositorios

import android.content.Context
import android.content.SharedPreferences

object UsuarioLocalRepositorio {

    private const val PREFERENCE_NAME = "ORGANIZADOR_TAREAS"
    private const val USER_ID = "USER_ID"


    fun getIdUsuario(context: Context): String? {
        val pref: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return pref.getString(USER_ID, "")
    }

    fun setIdUsuario(context: Context, idUsuario: String) {
        val pref: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        pref.edit().putString(USER_ID, idUsuario).apply()
    }

    fun cerrarSesion(context: Context) {
        val pref: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        pref.edit().remove(USER_ID).apply()
    }
}