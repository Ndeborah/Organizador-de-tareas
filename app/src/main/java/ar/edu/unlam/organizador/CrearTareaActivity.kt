package ar.edu.unlam.organizador

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ar.edu.unlam.organizador.database.entidades.Grupo
import ar.edu.unlam.organizador.database.entidades.Tarea
import ar.edu.unlam.organizador.database.repositorios.TareaRepositorio
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme

class CrearTareaActivity : ComponentActivity() {
    private lateinit var nuevaTarea: Tarea
    var nombreTarea: String = ""
    val grupo = Grupo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        val nombre: String? = bundle?.getString("nombre")

        setContent {
            OrganizadorTheme {
                Base(nombre!!)
            }
        }
    }

    @Composable
    private fun Base(nombre: String) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Column {
                CrearTarea(nombre)
            }
        }
    }

    @Composable
    private fun CrearTarea(nombre: String) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Column() {
                Text(text = "Crear Tarea")
            }
        }
        Spacer(modifier = Modifier.size(100.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Column {
                IngresarNombre().toString()
            }
        }
        Spacer(modifier = Modifier.size(100.dp))
        Column() {
            Button(onClick = {
                if (nombreTarea != "") {
                    nuevaTarea = Tarea(nombre = nombreTarea, grupo = nombre)
                    TareaRepositorio.save(nuevaTarea)
                    irAGrupo(nombre)
                    finish()
                }

            }) {
                Text(text = "Crear")
            }
            Button(onClick = { finish() }) {
                Text(text = "Cancelar")
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun IngresarNombre(){
        var text by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField (
            value = text,
            onValueChange = { text = it } ,
            label = { Text(text = "Ingresar el nombre de la Tarea")},
            singleLine = true,
            modifier = Modifier.padding(top = 20.dp)
        )
        if(text.text.isNotEmpty()) {
            nombreTarea = text.text
        }
    }

    private fun irAGrupo(nombre: String) {
        val intent = Intent(this, GrupoActivity::class.java).apply {
            putExtra("nombre", nombre)
        }
        startActivity(intent)
    }
}