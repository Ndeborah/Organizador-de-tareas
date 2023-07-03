package ar.edu.unlam.organizador

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.unlam.organizador.data.entidades.Usuario
import ar.edu.unlam.organizador.ui.componentes.Menu
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme
import ar.edu.unlam.organizador.ui.viewmodels.UsuarioViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsuarioActivity : ComponentActivity() {
    private val usuarioViewModel by viewModels<UsuarioViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val uiState by usuarioViewModel.uiState.collectAsState()
            usuarioViewModel.getUsuario(applicationContext)
            OrganizadorTheme {
                if (uiState.loading) {
                    CircularProgressIndicator()
                } else {
                    Base(applicationContext, uiState.usuario)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnrememberedMutableState")
    @Composable
    private fun Base(context: Context, usuario: Usuario) {
        Scaffold(
            topBar = { Menu(context, "usuario") }
        ) { paddingValues ->
            UsuarioCard(
                modifier = Modifier.padding(paddingValues),
                usuario = usuario,
                cerrarSesion = {
                    usuarioViewModel.cerrarSesion(applicationContext)
                    context.startActivity(Intent(context, MainActivity::class.java))
                }
            )
        }
    }

    @Composable
    private fun UsuarioCard(
        usuario: Usuario,
        cerrarSesion: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Card(modifier = modifier.fillMaxSize()) {
            Column(modifier = modifier.align(alignment = Alignment.CenterHorizontally)) {
                Row(modifier = modifier.align(alignment = Alignment.CenterHorizontally)) {
                    Text(text = usuario.nickname)
                }
                Row(modifier = modifier.align(alignment = Alignment.CenterHorizontally)) {
                    Text(text = usuario.numeroTelefono)
                }
                Row(modifier = modifier.align(alignment = Alignment.CenterHorizontally)) {
                    Button(
                        onClick = cerrarSesion,
                    ) {
                        Text(text = "Cerrar sesión")
                    }
                }

            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun vista() {
        Scaffold {
            UsuarioCard(
                usuario = Usuario("Prueba", "123456"),
                cerrarSesion = { /*TODO*/ },
                modifier = Modifier.padding(it)
            )
        }
    }
}

