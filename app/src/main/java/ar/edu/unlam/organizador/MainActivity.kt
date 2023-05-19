package ar.edu.unlam.organizador

import android.content.Context
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ar.edu.unlam.organizador.entidades.Tarea
import ar.edu.unlam.organizador.repositorios.TareaRepositorio
import ar.edu.unlam.organizador.ui.componentes.Menu
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrganizadorTheme {
                Base(this)
            }
        }
    }

    @Composable
    private fun Base(context: Context) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Column {
                Menu(context)
                MostrarTareas(TareaRepositorio.tareasPendientes)
                MostrarTareas(TareaRepositorio.tareasRealizadas)
            }
        }
    }


    @Composable
    private fun MostrarTareas(tareas: MutableList<Tarea>) {
        LazyColumn(
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(tareas) { tarea ->
                ListItemRow(tarea)
            }
        }
    }

    @Composable
    private fun ListItemRow(tarea: Tarea) {
        onStop()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .clickable(enabled = true, onClick = { ingresarAGrupo(tarea.nombre) })
        ) {
            Column {
                Text(text = tarea.nombre)
                Text(
                    text = "Pendientes: ${
                        TareaRepositorio.obtenerListaDeTareasPendientesPorGrupo(
                            tarea.nombre
                        ).size
                    }"
                )
            }
        }
    }


    private fun ingresarAGrupo(nombre: String) {
        val intent = Intent(this, GrupoActivity::class.java).apply {
            putExtra("nombre", nombre)
        }
        startActivity(intent)
    }

}
