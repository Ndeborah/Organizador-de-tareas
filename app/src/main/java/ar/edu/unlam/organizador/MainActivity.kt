package ar.edu.unlam.organizador

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.organizador.data.entidades.Grupo
import ar.edu.unlam.organizador.data.entidades.Tarea
import ar.edu.unlam.organizador.data.entidades.getTareas
import ar.edu.unlam.organizador.ui.componentes.AltaUsuarioForm
import ar.edu.unlam.organizador.ui.componentes.Menu
import ar.edu.unlam.organizador.ui.screens.ChatDeGrupos
import ar.edu.unlam.organizador.ui.screens.CrearGrupo
import ar.edu.unlam.organizador.ui.screens.CrearTarea
import ar.edu.unlam.organizador.ui.screens.ListaDeGrupos
import ar.edu.unlam.organizador.ui.screens.UsuarioScreen
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme
import ar.edu.unlam.organizador.ui.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainActivityViewModel = hiltViewModel()
) {
    val controller = rememberNavController()
    val mainUiState by mainViewModel.uiState.collectAsState()
    val usuarioState by mainViewModel.usuarioState.collectAsState()


    mainViewModel.getUsuarioLocal()
    OrganizadorTheme {
        if (mainUiState.loading) {
            CircularProgressIndicator()
        } else {
            if (usuarioState.exists) {
                mainViewModel.traerTodo()
                Scaffold(
                    topBar = { Menu("tareas", controller = controller) }
                ) { test ->
                    NavHost(navController = controller, startDestination = "tareas") {
                        composable(
                            "chats"
                        ) {
                            ChatDeGrupos(modifier = Modifier.padding(test))
                        }
                        composable("grupos") {
                            ListaDeGrupos(controller = controller)
                        }
                        composable("tareas") {
                            ListaTareas(controller, modifier = Modifier.padding(test))
                        }
                        composable("tareas/create/{id}") { navBackStackEntry ->
                            CrearTarea(navBackStackEntry.arguments?.getString("id")!!)
                        }
                        composable("grupos/create") {
                            CrearGrupo()
                        }
                        composable("usuario") {
                            UsuarioScreen()
                        }
                    }
                }
            } else {
                AltaUsuarioForm(
                    nombre = mainUiState.currentName,
                    cambioDeValorNombre = mainViewModel::actualizarNombre,
                    erroresNombre = mainUiState.currentNameErrors,
                    numero = mainUiState.currentTelefono,
                    cambioDeValorNumero = mainViewModel::actualizarTelefono,
                    erroresNumero = mainUiState.currentTelefonoErrors,
                    accionAceptar = { mainViewModel.ingresarUsuario() },
                    validData = mainUiState.validForm
                )
            }
        }
    }
}

@Composable
fun ListaTareas(
    controller: NavHostController,
    modifier: Modifier = Modifier,
    mainViewModel: MainActivityViewModel = hiltViewModel(),
) {
    val mainUiState by mainViewModel.uiState.collectAsState()
    Surface(modifier = modifier) {
        Base(
            mainUiState.grupos,
            createAction = { id ->
                controller.navigate("tareas/create/${id}")
            },
            deleteAction = { id ->
                mainViewModel.borrarTareaById(id)
            },
            completeAction = { grupoId, taskId ->
                // Le cambia a la tarea el estado de realizado por el opuesto
                // Actualiza la tarea en el repositorio
                // Llama a la acción de cambiar de estado del view model.
                mainViewModel.switchStatusTarea(grupoId, taskId)
            }
        )
    }
}

@Composable
private fun Base(
    grupos: List<Grupo>,
    createAction: (String) -> Unit,
    deleteAction: (String) -> Unit,
    completeAction: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ListaTareasPorGrupo(
            grupos = grupos,
            deleteAction = deleteAction,
            createAction = createAction,
            completeAction = completeAction
        )
    }
}

@Composable
private fun ListaTareasPorGrupo(
    grupos: List<Grupo>,
    createAction: (String) -> Unit,
    deleteAction: (String) -> Unit,
    completeAction: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(1.dp),
        modifier = modifier
    ) {
        item {
            Separador(
                text = "Pendientes",
                modifier = Modifier.background(color = MaterialTheme.colorScheme.primary),
                textColor = MaterialTheme.colorScheme.background
            )

        }
        //
        items(grupos) { grupo ->
            val tareas = grupo.getTareas().filter { !it.realizada }.toMutableList()
            TareasDeGrupo(
                grupo = grupo,
                tareas = tareas,
                createAction = createAction,
                deleteAction = deleteAction,
                completeAction = completeAction
            )
        }
        item {
            Separador(
                text = "Realizadas",
                modifier = Modifier.background(color = MaterialTheme.colorScheme.primary),
                textColor = MaterialTheme.colorScheme.background
            )
        }
        items(grupos) { grupo ->
            val tareas = grupo.getTareas().filter { it.realizada }.toMutableList()
            TareasDeGrupo(
                grupo = grupo,
                tareas = tareas,
                createAction = createAction,
                deleteAction = deleteAction,
                completeAction = completeAction
            )
        }
    }
}

@Composable
fun SeparadorConAccion(
    text: String,
    accion: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.primary,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(text = text, modifier = Modifier.align(Alignment.Center), color = textColor)
        IconButton(
            onClick = accion,
        ) {
            Icon(
                imageVector = Icons.Filled.Add, contentDescription = "Crear Tarea"
            )
        }
    }
}

@Composable
private fun Separador(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.primary,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(text = text, modifier = Modifier.align(Alignment.Center), color = textColor)
    }
}

@Composable

private fun TareaListItem(
    idGrupo: String,
    item: Tarea, deleteAction: (String) -> Unit, taskAction: (String, String) -> Unit
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
                IconButton(onClick = { taskAction(idGrupo, item.id) }) {
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
fun TareasDeGrupo(
    grupo: Grupo,
    tareas: MutableList<Tarea>,
    createAction: (String) -> Unit,
    deleteAction: (String) -> Unit,
    completeAction: (String, String) -> Unit
) {
    Column {
        Row {
            SeparadorConAccion(
                text = "Tareas de ${grupo.nombre}",
                accion = { createAction(grupo.id) },
                modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer)
            )
        }

        tareas.forEach {
            TareaListItem(grupo.id, it, deleteAction, completeAction)
        }
    }
}
