package ar.edu.unlam.organizador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import ar.edu.unlam.organizador.entidades.Grupo
import ar.edu.unlam.organizador.repositorios.GrupoRepositorio
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme

class GrupoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        val nombre: String? = bundle?.getString("nombre")
        val grupo: Grupo = GrupoRepositorio.ingresar(nombre!!)

        setContent {
            OrganizadorTheme {
                Text(grupo.nombre)
            }
        }
    }
}