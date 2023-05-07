package ar.edu.unlam.organizador

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ar.edu.unlam.organizador.entidades.Grupo
import ar.edu.unlam.organizador.repositorios.GrupoRepositorio
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme
import ar.edu.unlam.organizador.ui.theme.Pink80
import ar.edu.unlam.organizador.ui.theme.Purple40

class ChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        val nombre: String? = bundle?.getString("nombre")
        val grupo: Grupo = GrupoRepositorio.ingresar(nombre!!)

        setContent {
            OrganizadorTheme {
                Base(grupo, nombre)
            }
        }
    }

    @Composable
    private fun Base(grupo: Grupo, nombre: String) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Column {
                Menu(nombre)
                NombreDeGrupo(grupo)
            }
        }
    }

    @Composable
    private fun Menu(nombre: String) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Purple40),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {}
            ) {
                Text(text = "Chat", color = Pink80)
            }
            Button(
                onClick = {
                    irAGrupos(nombre)
                    onStop()
                },
            ) {
                Text(text = "Grupos")
            }
            Button(
                onClick = {
                    irATareas(nombre)
                    onStop()
                }
            ) {
                Text(text = "Tareas")
            }
        }
    }

    @Composable
    private fun NombreDeGrupo(grupo: Grupo) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Column() {
                Text(text = grupo.nombre)
            }
        }
    }

    private fun irATareas(nombre: String) {
        val intent = Intent(this, TareasActivity::class.java).apply {
            putExtra("nombre", nombre)
        }
        startActivity(intent)
    }

    private fun irAGrupos(nombre: String) {
        val intent = Intent(this, GrupoActivity::class.java).apply {
            putExtra("nombre", nombre)
        }
        startActivity(intent)
    }
}