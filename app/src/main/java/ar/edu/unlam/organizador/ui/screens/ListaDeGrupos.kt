package ar.edu.unlam.organizador.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.organizador.data.entidades.Grupo
import ar.edu.unlam.organizador.data.entidades.getTareas
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme
import ar.edu.unlam.organizador.ui.viewmodels.ListaDeGruposViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaDeGrupos(
    controller: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ListaDeGruposViewModel = hiltViewModel()
) {
    viewModel.setUp()
    val uiState = viewModel.uiState.collectAsState()
    OrganizadorTheme {
        Scaffold(
            bottomBar = {
                Botones(controller)
            }
        ) {
            if (uiState.value.loading) {
                CircularProgressIndicator()
            } else {
                Body(
                    controller = controller,
                    grupos = uiState.value.grupos,
                    modifier = modifier.padding(it),
                    deleteAction = { idGrupo ->
                        viewModel.deleteGroup(idGrupo)
                    }
                )
            }
        }
    }
}

@Composable
private fun Body(
    controller: NavHostController,
    grupos: MutableCollection<Grupo>,
    modifier: Modifier = Modifier,
    deleteAction: (String) -> Unit
) {
    // A surface container using the 'background' color from the theme
    Column(modifier = modifier) {
        MostrarGrupos(controller, grupos, deleteAction = deleteAction)
    }
}

@Composable
private fun MostrarGrupos(
    controller: NavHostController,
    grupos: MutableCollection<Grupo>,
    deleteAction: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(grupos.toList()) { grupo ->
            FilaDeGrupo(
                grupo,
                grupo.getTareas().filter { !it.realizada }.size,
                grupo.getTareas().filter { it.realizada }.size,
                shareAction = { controller.navigate("grupos/share/${it}") },
                deleteAction = deleteAction
            )
        }
        item {
            Button(
                onClick = { throw RuntimeException("Test Crash") },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(text = "DANGER")
            }
        }
    }
}

@Composable
private fun FilaDeGrupo(
    grupo: Grupo,
    totalPendientes: Int,
    totalRealizadas: Int,
    shareAction: (String) -> Unit,
    deleteAction: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .clickable(enabled = true, onClick = { })
    ) {
        Column {
            Text(text = grupo.nombre)
            Text(
                text = "Pendientes: $totalPendientes"
            )
            Text(
                text = "Realizadas: $totalRealizadas"
            )
            //los parentesis del row sirven para pasarle parametros opcionales
            Row() {
                IconButton(onClick = { shareAction(grupo.id) }) {
                    Icon(
                        imageVector = Icons.Filled.Share, contentDescription = "Unirse a un grupo"
                    )
                }
                IconButton(onClick = { deleteAction(grupo.id) }) {
                    Icon(
                        imageVector = Icons.Filled.Delete, contentDescription = "Borrar grupo"
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun PreviewBotones() {
    val navController = rememberNavController()
    Botones(navController)
}

@Composable
private fun Botones(navController: NavController) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Unirse a un grupo",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            selected = false,
            onClick = {
                navController.navigate("grupos/create")
            },
            label = {
                Text(
                    "Crear Grupo",
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Unirse a un grupo",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            selected = false,
            onClick = {
                navController.navigate("grupos/join")
            },
            label = {
                Text(
                    "Unirse A Un Grupo",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        )
    }
    //BOTÓN PRUEBA CRASHLYTICS


}