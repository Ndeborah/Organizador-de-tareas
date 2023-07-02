package ar.edu.unlam.organizador

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ar.edu.unlam.organizador.data.entidades.Grupo
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
                        mainViewModel.loadTasks()
                        Base(this)
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

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnrememberedMutableState")
    @Composable
    private fun Base(context: Context) {
        // A surface container using the 'background' color from the theme
        Scaffold(
            topBar = { Menu(context) }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                MostrarGrupos(mainViewModel.grupos)
                Botones()
            }
        }
    }

    @Composable
    private fun MostrarGrupos(grupos: MutableList<Grupo>) {
        val tareas by mainViewModel.tareas.collectAsState(initial = emptyList())
        if (tareas.isEmpty()) {
            CircularProgressIndicator()
        } else {
            Box(
                modifier = Modifier
                    .height(430.dp)
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(10.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(grupos) { grupo ->
                        FilaDeGrupo(
                            grupo,
                            tareas.filter { it.grupo == grupo.nombre && !it.realizada }.size,
                            tareas.filter { it.grupo == grupo.nombre && it.realizada }.size
                        )
                    }
                }
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
                .clickable(enabled = true, onClick = { nombreDeGrupo(grupo.nombre) })
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

    private fun nombreDeGrupo(nombre: String) {
        val intent = Intent(this, TareasDeGrupoActivity::class.java).apply {
            putExtra("nombre", nombre)
        }
        startActivity(intent)
    }

    @Composable
    private fun Botones() {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(modifier = Modifier.width(170.dp), shape = RoundedCornerShape(10), onClick = {
                irACrearGrupo()
                onStop()
            }) {
                Text("Crear Grupo", color = Color.White)
            }
            Button(
                modifier = Modifier.width(170.dp),
                shape = RoundedCornerShape(10),
                onClick = { /*TODO*/ }) {
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
}
