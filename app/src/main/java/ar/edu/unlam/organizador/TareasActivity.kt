package ar.edu.unlam.organizador

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.organizador.database.entidades.Tarea
import ar.edu.unlam.organizador.ui.componentes.Menu
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme
import ar.edu.unlam.organizador.ui.viewmodels.TareasViewModel

class TareasActivity : ComponentActivity() {

    private val viewModel by viewModels<TareasViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val nombre: String? = bundle?.getString("nombre")

        setContent {
            // Esta val de tareas lo que hace es "escuchar" las tareas que hay en el view model.
            // Si hay algún cambio, actualiza solo
            // Por defecto empieza como una lista vacía
            val tareas by viewModel.tareas.observeAsState(initial = emptyList())
            OrganizadorTheme {
                // Si está vacío significa que todavía no tenemos datos obtenidos desde firebase
                // Porende, mostramos un progress
                if (tareas.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    // Ahora ya tenemos datos, por ende podemos usar la lista de tareas
                    // Filtramos las tareas del grupo que queremos
                    // Usamos las funciones deleteTask y switchStatus para cuando se quiera
                    // borrar una tarea o cambiarle el estado de completitud
                    Base(
                        this,
                        tareas.filter { it.grupo == nombre!! },
                        nombre!!,
                        this::deleteTask,
                        this::switchStatus
                    )
                }

            }
        }
    }

    // Llama a la acción de borrar que haya en el view model.
    private fun deleteTask(taskId: String) {
        viewModel.deleteTarea(taskId)
    }

    // Llama a la acción de cambiar de estado del view model.
    private fun switchStatus(taskId: String) {
        viewModel.switchStatus(taskId)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Base(
        context: Context,
        tareas: List<Tarea>,
        nombre: String,
        deleteAction: (String) -> Unit,
        completeAction: (String) -> Unit
    ) {
        // A surface container using the 'background' color from the theme
        Scaffold(
            topBar = {
                Menu(context)
            },
            floatingActionButton = { BotonAgregar(nombre) }
        ) { padding ->
            Column(modifier = Modifier.padding(padding)) {
                TareasDeGrupo(nombre)
                Spacer(modifier = Modifier.size(5.dp))
                ListaTareas(
                    pendientes = tareas.filter { !it.realizada }.toMutableList(),
                    realizadas = tareas.filter { it.realizada }.toMutableList(),
                    deleteAction = deleteAction,
                    completeAction = completeAction
                )
            }
        }
    }

    @Composable
    private fun ListaTareas(
        pendientes: MutableList<Tarea>,
        realizadas: MutableList<Tarea>,
        deleteAction: (String) -> Unit,
        completeAction: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(1.dp),
            modifier = modifier
        ) {
            item {
                Separador(text = "Pendientes")
            }
            items(pendientes) { item ->
                TareaListItem(item, deleteAction, completeAction)
            }
            item {
                Separador(text = "Realizadas")
            }
            items(realizadas) { item ->
                TareaListItem(item, deleteAction, completeAction)
            }
        }
    }

    @Composable
    private fun Separador(text: String) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Text(text = text, modifier = Modifier.align(Alignment.Center))
        }
    }

    @Composable
    private fun TareaListItem(
        item: Tarea, deleteAction: (String) -> Unit, taskAction: (String) -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = item.nombre)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { deleteAction(item.id) },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete, contentDescription = "Agregar Tarea"
                        )
                    }
                    IconButton(onClick = { taskAction(item.id) }) {
                        val image = if (item.realizada) Icons.Filled.Clear else Icons.Filled.Check
                        Icon(
                            imageVector = image, contentDescription = "Agregar Tarea"
                        )
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    private fun itemcito() {
        TareasDeGrupo("Lele")
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    private fun screen() {
        OrganizadorTheme {
            Scaffold { paddingValues ->
                ListaTareas(
                    mutableListOf(
                        Tarea(
                            id = "1", nombre = "Tarea", grupo = "Grupo 5", realizada = false
                        ),
                        Tarea(
                            id = "1", nombre = "Tarea", grupo = "Grupo 5", realizada = false
                        )
                    ),
                    mutableListOf(
                        Tarea(
                            id = "1", nombre = "Realizada", grupo = "Grupo 5", realizada = true
                        )
                    ),
                    this::deleteTask, this::switchStatus,
                    modifier = Modifier.padding(paddingValues)
                )
            }

        }
    }

    @Composable
    private fun BotonAgregar(nombre: String) {
        FloatingActionButton(
            onClick = { irAAgregar(nombre) }, modifier = Modifier.size(40.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Tarea")
        }
    }

    private fun irAAgregar(nombre: String) {
        val intent = Intent(this, CrearTareaActivity::class.java).apply {
            putExtra("nombre", nombre)
        }
        startActivity(intent)
    }

    @Composable
    private fun TareasDeGrupo(nombre: String) {
        val offset = Offset(5.0f, 10.0f)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.Center)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Tareas de $nombre",
                    style = TextStyle(
                        fontSize = 24.sp,
                        shadow = Shadow(
                            color = Color.LightGray,
                            offset = offset,
                            blurRadius = 7f
                        )
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}