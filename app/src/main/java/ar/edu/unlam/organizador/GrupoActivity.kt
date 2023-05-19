package ar.edu.unlam.organizador

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import ar.edu.unlam.organizador.ui.componentes.Menu
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme

class GrupoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        val nombre: String? = bundle?.getString("nombre")
        val grupo: Grupo = GrupoRepositorio.buscarGrupo(nombre!!)

        setContent {
            OrganizadorTheme {
                Base(this, grupo, nombre)
            }
        }
    }

    @Composable
    private fun Base(context: Context, grupo: Grupo, nombre: String) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Column {
                Menu(context)
                NombreDeGrupo(grupo)
                Participantes()
                Botones()
                Salir()
            }
        }
    }

    @Composable
    private fun Botones() {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                irACrearGrupo()
                onStop()
            }) {
                Text("Crear Grupo", color = Color.White)
            }
            Button(onClick = { /*TODO*/ }) {
                Text("Unirse a un Grupo", color = Color.White)
            }
            //BOTÓN PRUEBA CRASHLYTICS

            /*Button(onClick = {throw RuntimeException("Test Crash")}) {
                Text(text = "Prueba")
            }*/
        }
    }

    private fun irACrearGrupo() {
        val intent = Intent(this, CrearGrupoActivity::class.java)
        startActivity(intent)
    }

    @Composable
    private fun NombreDeGrupo(grupo: Grupo) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Column {
                Text(text = grupo.nombre)
            }
        }
    }

    @Composable
    private fun Participantes() {
        Spacer(modifier = Modifier.size(5.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Column {
                Text(text = "Participantes")
            }
        }
        Spacer(modifier = Modifier.size(5.dp))
    }

    @Composable
    private fun Salir() {
        Column {
            Button(onClick = {
                irAMain()
                finish()
            }) {
                Text(text = "Salir")
            }
        }
    }


    private fun irAMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}