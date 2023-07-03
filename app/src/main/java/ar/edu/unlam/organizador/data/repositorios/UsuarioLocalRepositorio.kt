package ar.edu.unlam.organizador.data.repositorios

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Singleton

interface UsuarioLocalRepositorio {
    fun getIdUsuario(context: Context): String?

    fun setIdUsuario(context: Context, idUsuario: String)

    fun cerrarSesion(context: Context)
}