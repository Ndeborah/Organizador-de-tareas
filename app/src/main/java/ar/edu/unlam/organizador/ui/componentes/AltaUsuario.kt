package ar.edu.unlam.organizador.ui.componentes


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AltaUsuarioForm(
    texto: String,
    cambioDeValorNombre: (String) -> Unit,
    erroresNombre: MutableList<String>,
    numero: String,
    cambioDeValorNumero: (String) -> Unit,
    erroresNumero: MutableList<String>,
    accionAceptar: () -> Unit,
    validData: Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        //Nombre
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = texto,
                onValueChange = {
                    cambioDeValorNombre(it)
                },
                label = { Text("Ingrese nombre") },
            )
            erroresNombre.forEach {
                Text(
                    color = Color.Red,
                    text = it,
                    modifier = Modifier.padding(vertical = 8.dp),
                )
            }
        }

        //Telefono
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = numero,
                onValueChange = { cambioDeValorNumero(it) },
                label = { Text("Ingrese número de teléfono") },
            )
            erroresNumero.forEach {
                Text(
                    color = Color.Red,
                    text = it,
                    modifier = Modifier.padding(vertical = 8.dp),
                )
            }
        }
        Button(
            onClick = accionAceptar,
            enabled = validData,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Aceptar")
        }
    }


}