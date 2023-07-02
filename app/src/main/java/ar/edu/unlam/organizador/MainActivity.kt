package ar.edu.unlam.organizador

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.edu.unlam.organizador.data.entidades.Grupo
import ar.edu.unlam.organizador.data.entidades.Tarea
import ar.edu.unlam.organizador.data.repositorios.TareaRepositorio
import ar.edu.unlam.organizador.ui.componentes.AltaUsuarioForm
import ar.edu.unlam.organizador.ui.componentes.Menu
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme
import ar.edu.unlam.organizador.ui.viewmodels.MainActivityViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainActivityViewModel>()

    //Con esta función se crea el usuario se finaliza la activity inicial y re vuelve a iniciar ya en la vista principal.
    //Porque la activity de inicio es la activity principal cuando hay un usuario.
    private fun access() {
        mainViewModel.ingresarUsuario(applicationContext)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainUiState by mainViewModel.uiState.collectAsState()
            val usuarioState by mainViewModel.usuarioState.collectAsState()
            mainViewModel.getUsuarioLocal(applicationContext)
            OrganizadorTheme {
                if (mainUiState.loading) {
                    CircularProgressIndicator()
                } else {
                    if (usuarioState.exists) {
                        mainViewModel.traerTodo()
                        Scaffold(
                            topBar = { Menu(context = applicationContext, "tareas") }
                        ) {
                            Base(
                                mainUiState.tareas,
                                mainUiState.grupos,
                                deleteAction = this::deleteTarea,
                                completeAction = this::switchStatus,
                                modifier = Modifier.padding(it)
                            )
                        }
                    } else {
                        AltaUsuarioForm(
                            nombre = mainUiState.currentName,
                            cambioDeValorNombre = mainViewModel::actualizarNombre,
                            erroresNombre = mainUiState.currentNameErrors,
                            numero = mainUiState.currentTelefono,
                            cambioDeValorNumero = mainViewModel::actualizarTelefono,
                            erroresNumero = mainUiState.currentTelefonoErrors,
                            accionAceptar = this::access,
                            validData = mainUiState.validForm
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun Base(
        tareas: List<Tarea>,
        grupos: MutableList<Grupo>,
        deleteAction: (String) -> Unit,
        completeAction: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(modifier = modifier) {
            ListaTareasPorGrupo(
                tareasPendientes = tareas.filter { !it.realizada }.toMutableList(),
                tareasRealizadas = tareas.filter { it.realizada }.toMutableList(),
                grupos = grupos,
                deleteAction = deleteAction,
                completeAction = completeAction
            )
        }
    }

    @Composable
    private fun ListaTareasPorGrupo(
        tareasPendientes: MutableList<Tarea>,
        tareasRealizadas: MutableList<Tarea>,
        grupos: MutableList<Grupo>,
        deleteAction: (String) -> Unit,
        completeAction: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        if (grupos.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(1.dp),
                modifier = modifier
            ) {
                item {
                    Separador(text = "Pendientes")
                }
                items(grupos) { grupo ->
                    val tareas = tareasPendientes.filter {
                        it.grupo == grupo.nombre
                    }.toMutableList()
                    TareasDeGrupo(
                        grupo = grupo,
                        tareas = tareas,
                        deleteAction = deleteAction,
                        completeAction = completeAction
                    )
                }
                item {
                    Separador(text = "Realizadas")
                }
                items(grupos) { grupo ->
                    val tareas = tareasRealizadas.filter {
                        it.grupo == grupo.nombre
                    }.toMutableList()
                    TareasDeGrupo(
                        grupo = grupo,
                        tareas = tareas,
                        deleteAction = deleteAction,
                        completeAction = completeAction
                    )
                }
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
                            imageVector = Icons.Filled.Delete, contentDescription = "Borrar Tarea"
                        )
                    }
                    IconButton(onClick = { taskAction(item.id) }) {
                        val image = if (item.realizada) Icons.Filled.Clear else Icons.Filled.Check
                        Icon(
                            imageVector = image, contentDescription = "Check or Clear Tarea"
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun BotonAgregar(nombre: String) {
        FloatingActionButton(
            onClick = { irAAgregarTarea(nombre) }, modifier = Modifier.size(40.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Tarea")
        }
    }

    private fun irAAgregarTarea(nombreDelGrupo: String) {
        val intent = Intent(this, CrearTareaActivity::class.java)
        startActivity(intent)
    }

    @Composable
    fun TareasDeGrupo(
        grupo: Grupo,
        tareas: MutableList<Tarea>,
        deleteAction: (String) -> Unit,
        completeAction: (String) -> Unit
    ) {
        if (tareas.isNotEmpty()) {
            Column {
                Separador(text = "Tareas de ${grupo.nombre}")
                tareas.forEach {
                    TareaListItem(it, deleteAction, completeAction)
                }

            }
        }
    }

    private fun deleteTarea(tarea: String) {
        TareaRepositorio.deleteByID(tarea)
    }

    // Se trae la tarea del repositorio usando el id de la tarea.
    // Le cambia a la tarea el estado de realizado por el opuesto
    // Actualiza la tarea en el repositorio
    // Llama a la acción de cambiar de estado del view model.
    private fun switchStatus(taskId: String) {
        mainViewModel.switchStatusTarea(taskId)
    }
}
