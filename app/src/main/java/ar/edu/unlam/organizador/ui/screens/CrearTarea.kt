package ar.edu.unlam.organizador.ui.screens

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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.organizador.data.entidades.Tarea
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme
import ar.edu.unlam.organizador.ui.theme.Purple40
import ar.edu.unlam.organizador.ui.viewmodels.CrearTareaViewModel


var nombreTarea: String = ""

@Composable
fun CrearTarea(idGrupo: String, viewModel: CrearTareaViewModel = hiltViewModel()) {
    OrganizadorTheme {

        Base(crearAction = { nombreTarea ->
            viewModel.save(idGrupo, Tarea(nombre = nombreTarea))
        })
    }
}


@Composable
private fun Base(crearAction: (String) -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        CrearTareaCard(crearAction)
    }
}

@Composable
private fun CrearTareaCard(crearAction: (String) -> Unit) {
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
            crearAction(nombreTarea)
        }) {
            Text(text = "Crear")
        }
        Button(modifier = Modifier.width(170.dp),
            shape = RoundedCornerShape(10),
            onClick = { }) {
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
