package ar.edu.unlam.organizador.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.organizador.ui.viewmodels.UnirseAGrupoViewModel

@Composable
fun UnirseAGrupo(
    controller: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: UnirseAGrupoViewModel = hiltViewModel()
) {
    val formState by viewModel.formState.collectAsState()
    Surface(modifier = modifier) {
        UnirseAGrupoForm(
            id = formState.currentId,
            accionAceptar = {
                viewModel.agregarGrupo(
                    onSuccess = { controller.navigate("grupos") },
                    onError = {
                        Log.i("UNIRSE GRUPO", it)
                    }
                )
            },
            cambioDeValorId = viewModel::validarId,
            erroresId = formState.currentIdErrors,
            validData = formState.validForm
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnirseAGrupoForm(
    id: String,
    accionAceptar: () -> Unit,
    cambioDeValorId: (String) -> Unit,
    erroresId: MutableList<String>,
    validData: Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = id,
                onValueChange = {
                    cambioDeValorId(it)
                },
                label = { Text("Ingrese nombre") },
            )
            erroresId.forEach {
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