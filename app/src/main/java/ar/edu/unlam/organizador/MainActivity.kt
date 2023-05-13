package ar.edu.unlam.organizador

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import ar.edu.unlam.organizador.repositorios.TareaRepositorio
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme
import ar.edu.unlam.organizador.ui.theme.Pink80
import ar.edu.unlam.organizador.ui.theme.Purple40
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

lateinit var firebaseAnalytics: FirebaseAnalytics

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics

        setContent {
            OrganizadorTheme {
                Base()
            }
        }
    }

    @Composable
    private fun Base() {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Column {
                Menu()
                MostrarGrupos(GrupoRepositorio.grupos)
                Botones()
            }
        }
    }

    @Composable
    private fun Menu() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Purple40),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    irAChat()
                    onStop()
                }
            ) {
                Text(text = "Chat")
            }
            Button(
                onClick = {}
            ) {
                Text(text = "Grupos", color = Pink80)
            }
            Button(
                onClick = {
                    irATareas()
                    onStop()
                }
            ) {
                Text(text = "Tareas")
            }
        }
    }

    @Composable
    private fun MostrarGrupos(datos: MutableList<Grupo>) {
        LazyColumn(
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(datos) { item ->
                ListItemRow(item)
            }
        }
    }

    @Composable
    private fun ListItemRow(item: Grupo) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .clickable(enabled = true, onClick = { ingresarAGrupo(item.nombre) })
        ) {
            Column {
                Text(text = item.nombre)
                Text(
                    text = "Pendientes: ${
                        TareaRepositorio.obtenerListaDeTareasPendientesPorGrupo(
                            item.nombre
                        ).size
                    }"
                )
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
                val bundle = Bundle()
                val Grupo = ""
                bundle.putString(FirebaseAnalytics.Param.GROUP_ID, Grupo)
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.JOIN_GROUP, bundle)

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

    private fun irAChat() {
        val intent = Intent(this, ChatDeGrupoActivity::class.java).apply {
            // Harcodeamos esto de momento hasta que tengamos la vista de Grupos resuelta.
            putExtra("nombre", "Grupo 1")
        }
        startActivity(intent)
    }

    private fun irATareas() {
        val intent = Intent(this, TareasDeGrupoActivity::class.java).apply {
            // Harcodeamos esto de momento hasta que tengamos la vista de Tareas resuelta.
            putExtra("nombre", "Grupo 1")
        }
        startActivity(intent)
    }

    private fun ingresarAGrupo(nombre: String) {
        val intent = Intent(this, GrupoActivity::class.java).apply {
            putExtra("nombre", nombre)
        }
        startActivity(intent)
    }

}
