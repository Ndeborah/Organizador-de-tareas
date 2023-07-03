package ar.edu.unlam.organizador

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.organizador.data.entidades.Usuario
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme
import ar.edu.unlam.organizador.ui.theme.Purple40
import ar.edu.unlam.organizador.ui.viewmodels.CrearGrupoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CrearGrupoActivity : ComponentActivity() {
    private val viewModel: CrearGrupoViewModel by viewModels()
    var nombre: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val uiState by viewModel.uiState.collectAsState()
            viewModel.getUsuarioLocal(applicationContext)
            OrganizadorTheme {
                if (uiState.loading) {
                    CircularProgressIndicator()
                } else {
                    Base(uiState.usuario)
                }
            }
        }
    }

    @Composable
    private fun Base(usuario: Usuario) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                CrearGrupo(usuario)
            }
        }
    }

    @Composable
    private fun CrearGrupo(usuario: Usuario) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Purple40),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Crear Grupo", fontSize = 20.sp, color = White)
            }
        }
        Spacer(modifier = Modifier.size(100.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Column {
                IngresarNombre().toString()
            }
        }
        Spacer(modifier = Modifier.size(100.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(modifier = Modifier.width(170.dp), shape = RoundedCornerShape(10), onClick = {
                viewModel.crearGrupo(nombre, usuario.numeroTelefono)
                irAMain()
                finish()
            }) {
                Text(text = "Crear")
            }
            Button(modifier = Modifier.width(170.dp),
                shape = RoundedCornerShape(10),
                onClick = { finish() }) {
                Text(text = "Cancelar")
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun IngresarNombre() {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(value = text,
            onValueChange = { text = it },
            label = { Text(text = "Ingresar el nombre del nuevo Grupo") },
            singleLine = true,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        )
        nombre = text.text
    }

    private fun irAMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
