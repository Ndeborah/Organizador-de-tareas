package ar.edu.unlam.organizador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import ar.edu.unlam.organizador.data.entidades.Grupo
import ar.edu.unlam.organizador.data.entidades.Tarea
import ar.edu.unlam.organizador.data.repositorios.TareaRepositorio
import ar.edu.unlam.organizador.ui.componentes.Menu
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme
import ar.edu.unlam.organizador.ui.theme.Purple40

class CrearTareaActivity : ComponentActivity() {
    private lateinit var nuevaTarea: Tarea
    var nombreTarea: String = ""
    val grupo = Grupo()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        val nombre: String? = bundle?.getString("nombre")

        setContent {
            OrganizadorTheme {
                Scaffold(
                    topBar = { Menu(applicationContext, "tareas") },
                    bottomBar = {}
                ) { paddingValues ->
                    Base(modifier = Modifier.padding(paddingValues))
                }
            }
        }
    }

    @Composable
    private fun Base(modifier: Modifier = Modifier) {

    }

    @Composable
    private fun CrearTarea(nombre: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Purple40),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Crear Tarea", fontSize = 20.sp, color = White)
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
                if (nombreTarea != "") {
                    nuevaTarea = Tarea(nombre = nombreTarea, grupo = nombre)
                    TareaRepositorio.save(nuevaTarea)
                    finish()
                }

            }) {
                Text(text = "Crear")
            }
            Button(
                modifier = Modifier.width(170.dp),
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
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(text = "Ingresar el nombre de la Tarea") },
            singleLine = true,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        )
        if (text.text.isNotEmpty()) {
            nombreTarea = text.text
        }
    }
}