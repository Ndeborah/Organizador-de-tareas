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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                //Menu()
                MostrarGrupos(GrupoRepositorio.grupos)
                Botones()
            }
        }
    }

    @Composable
    private fun MostrarGrupos(datos: MutableList<Grupo>) {
        LazyColumn(
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(datos) {
                item -> ListItemRow(item)
            }
        }
    }

    @Composable
    private fun ListItemRow(item: Grupo) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp).clickable(enabled = true, onClick = {ingresarAGrupo(item.nombre)})
        ) {
            Column {
                Text(text = item.nombre)
                Text(text = "Pendientes: ${TareaRepositorio.obtenerListaDeTareasPendientesPorGrupo(item.nombre).size}")
            }
        }
    }

    @Composable
    private fun Botones() {
        Column (
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
        }
    }

    private fun irACrearGrupo() {
        val intent = Intent(this, CrearGrupoActivity::class.java)
        startActivity(intent)
    }

    private fun ingresarAGrupo(nombre: String) {
        val intent = Intent(this, GrupoActivity::class.java).apply {
            putExtra("nombre", nombre)
        }
        startActivity(intent)
    }
}
