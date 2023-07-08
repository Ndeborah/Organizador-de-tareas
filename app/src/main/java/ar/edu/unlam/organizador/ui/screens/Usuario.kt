package ar.edu.unlam.organizador.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.organizador.data.entidades.Usuario
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme
import ar.edu.unlam.organizador.ui.viewmodels.UsuarioViewModel


@Composable
fun UsuarioScreen(
    closeAction: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UsuarioViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    viewModel.getUsuario()
    OrganizadorTheme {
        if (uiState.loading) {
            CircularProgressIndicator()
        } else {
            Surface(modifier = modifier) {
                Base(uiState.usuario) {
                    viewModel.cerrarSesion()
                    closeAction()
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun Base(usuario: Usuario, closeSession: () -> Unit) {
    UsuarioCard(
        usuario = usuario,
        cerrarSesion = closeSession
    )
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
