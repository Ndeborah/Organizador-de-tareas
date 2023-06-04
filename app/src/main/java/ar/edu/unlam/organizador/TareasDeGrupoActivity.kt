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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ar.edu.unlam.organizador.database.entidades.Grupo
import ar.edu.unlam.organizador.database.entidades.Tarea
import ar.edu.unlam.organizador.database.repositorios.GrupoRepositorio
import ar.edu.unlam.organizador.database.repositorios.TareaRepositorio
import ar.edu.unlam.organizador.ui.componentes.Menu
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme

class TareasDeGrupoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*val bundle = intent.extras
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
                TareasPendientes(
                    datos = TareaRepositorio.obtenerListaDeTareasPendientesPorGrupo(
                        nombre
                    )
                )
                TareasRealizadas(
                    datos = TareaRepositorio.obtenerListaDeTareasRealizadasPorGrupo(
                        nombre
                    )
                )
                BotonAgregar(grupo = grupo)
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
        Spacer(modifier = Modifier.size(5.dp))
    }

    @Composable
    private fun TareasPendientes(datos: MutableList<Tarea>) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Column {
                Text(text = "Pendientes")
            }
        }
        Spacer(modifier = Modifier.size(5.dp))
        LazyColumn(
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(datos) { item ->
                ListItemRowPendiente(item)
            }
        }
    }

    @Composable
    private fun ListItemRowPendiente(item: Tarea) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Column {
                Text(text = item.nombre)
            }
        }
    }

    @Composable
    private fun TareasRealizadas(datos: MutableList<Tarea>) {
        Spacer(modifier = Modifier.size(5.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Column {
                Text(text = "Realizadas")
            }
        }
        Spacer(modifier = Modifier.size(5.dp))
        LazyColumn(
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(datos) { item ->
                ListItemRowRealizada(item)
            }
        }
    }

    @Composable
    private fun ListItemRowRealizada(item: Tarea) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Column {
                Text(text = item.nombre)
            }
        }
    }

    @Composable
    private fun BotonAgregar(grupo: Grupo) {
        FloatingActionButton(
            onClick = { irAAgregar(grupo.nombre) },
            modifier = Modifier.size(40.dp)
        ) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Agregar Tarea")
        }
    }

    private fun irAAgregar(nombre: String) {
        val intent = Intent(this, CrearTareaActivity::class.java).apply {
            putExtra("nombre", nombre)
        }
        startActivity(intent)
    }*/
    }
}