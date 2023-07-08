package ar.edu.unlam.organizador

import android.content.Context
import android.content.Intent.getIntent
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
import androidx.core.content.ContextCompat.startActivity
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
import ar.edu.unlam.organizador.ui.screens.CompartirGrupo
import ar.edu.unlam.organizador.ui.screens.CrearGrupo
import ar.edu.unlam.organizador.ui.screens.CrearTarea
import ar.edu.unlam.organizador.ui.screens.ListaDeGrupos
import ar.edu.unlam.organizador.ui.screens.UnirseAGrupo
import ar.edu.unlam.organizador.ui.screens.UsuarioScreen
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme
import ar.edu.unlam.organizador.ui.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(
                {
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    closeAction: () -> Unit,
    mainViewModel: MainActivityViewModel = hiltViewModel()
) {
    val controller = rememberNavController()
    val usuarioState by mainViewModel.usuarioState.collectAsState()
    val usuarioFormState by mainViewModel.usuarioFormState.collectAsState()
    mainViewModel.getUsuarioLocal()
    OrganizadorTheme {
        if (usuarioState.loading) {
            CircularProgressIndicator()
        } else {
            if (usuarioState.exists) {
                Scaffold(
                    topBar = { Menu(controller = controller) }
                ) { paddingValues ->
                    NavHost(navController = controller, startDestination = "tareas") {
                        composable(
                            "chats"
                        ) {
                            ChatDeGrupos(modifier = Modifier.padding(paddingValues))
                        }
                        composable("grupos") {
                            ListaDeGrupos(
                                controller = controller,
                                modifier = Modifier.padding(paddingValues)
                            )
                        }
                        composable("tareas") {
                            ListaTareas(controller, modifier = Modifier.padding(paddingValues))
                        }
                        composable("tareas/create/{id}") { navBackStackEntry ->
                            CrearTarea(controller, navBackStackEntry.arguments?.getString("id")!!)
                        }
                        composable("grupos/create") {
                            CrearGrupo()
                        }
                        composable("usuario") {
                            UsuarioScreen(
                                closeAction = closeAction,
                                modifier = Modifier.padding(paddingValues)
                            )
                        }
                        composable("grupos/join") {
                            UnirseAGrupo(controller, modifier = Modifier.padding(paddingValues))
                        }
                        composable("grupos/share/{id}") { navBackStackEntry ->
                            CompartirGrupo(
                                navBackStackEntry.arguments?.getString("id")!!,
                                modifier = Modifier.padding(paddingValues)
                            )
                        }
                    }
                }
            } else {
                AltaUsuarioForm(
                    nombre = usuarioFormState.currentName,
                    cambioDeValorNombre = mainViewModel::actualizarNombre,
                    erroresNombre = usuarioFormState.currentNameErrors,
                    numero = usuarioFormState.currentTelefono,
                    cambioDeValorNumero = mainViewModel::actualizarTelefono,
                    erroresNumero = usuarioFormState.currentTelefonoErrors,
                    accionAceptar = { mainViewModel.ingresarUsuario() },
                    validData = usuarioFormState.validForm
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
    val uiState by mainViewModel.uiState.collectAsState()
    val userState by mainViewModel.usuarioState.collectAsState()
    mainViewModel.traerTodo()
    if (uiState.loading || userState.loading) {
        CircularProgressIndicator()
    } else {
        Surface(modifier = modifier) {
            Base(
                uiState.grupos,
                createAction = { grupoId ->
                    controller.navigate("tareas/create/${grupoId}")
                },
                deleteAction = { grupoId, id ->
                    mainViewModel.borrarTareaById(grupoId, id)
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
}

@Composable
private fun Base(
    grupos: List<Grupo>,
    createAction: (String) -> Unit,
    deleteAction: (String, String) -> Unit,
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
    deleteAction: (String, String) -> Unit,
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
    item: Tarea, deleteAction: (String, String) -> Unit, taskAction: (String, String) -> Unit
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
                    onClick = { deleteAction(idGrupo, item.id) },
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
    deleteAction: (String, String) -> Unit,
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
