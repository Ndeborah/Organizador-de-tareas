package ar.edu.unlam.organizador

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import ar.edu.unlam.organizador.data.entidades.Grupo
import ar.edu.unlam.organizador.data.entidades.getTareas
import ar.edu.unlam.organizador.ui.componentes.Menu
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme
import ar.edu.unlam.organizador.ui.viewmodels.ListaDeGruposViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListaDeGruposActivity : ComponentActivity() {
    private val viewModel by viewModels<ListaDeGruposViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setUp(applicationContext)
        val bundle = intent.extras

        setContent {
            val uiState = viewModel.uiState.collectAsState()
            OrganizadorTheme {
                Scaffold(
                    topBar = { Menu(applicationContext, "grupos") },
                    bottomBar = {
                        Botones()
                    }
                ) {
                    if (uiState.value.loading) {
                        CircularProgressIndicator()
                    } else {
                        Body(
                            grupos = uiState.value.grupos,
                            modifier = Modifier.padding(it)
                        )
                    }
                }

            }
        }
    }

    @Composable
    private fun Body(
        grupos: MutableCollection<Grupo>,
        modifier: Modifier = Modifier
    ) {
        // A surface container using the 'background' color from the theme
        Column(modifier = modifier) {
            MostrarGrupos(grupos)
        }
    }

    @Composable
    private fun MostrarGrupos(grupos: MutableCollection<Grupo>) {
        LazyColumn(
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(grupos.toList()) { grupo ->
                FilaDeGrupo(
                    grupo,
                    grupo.getTareas().filter { !it.realizada }.size,
                    grupo.getTareas().filter { it.realizada }.size
                )
            }
        }
    }

    @Composable
    private fun FilaDeGrupo(grupo: Grupo, totalPendientes: Int, totalRealizadas: Int) {
        onStop()
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
            }
        }
    }

    @Preview
    @Composable
    fun PreviewBotones() {
        Botones()
    }

    @Composable
    private fun Botones() {
        NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Borrar Tarea",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                selected = false,
                onClick = {
                    irACrearGrupo()
                    onStop()
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
                        contentDescription = "Borrar Tarea",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                selected = false,
                onClick = {
                    irACrearGrupo()
                    onStop()
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

        /*Button(onClick = {throw RuntimeException("Test Crash")}) {
            Text(text = "Prueba")
        }*/
    }

    private fun irACrearGrupo() {
        val intent = Intent(this, CrearGrupoActivity::class.java)
        startActivity(intent)
    }
}