package ar.edu.unlam.organizador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme
import ar.edu.unlam.organizador.ui.theme.Pink80
import ar.edu.unlam.organizador.ui.theme.Purple40

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrganizadorTheme {
                Base()
            }
        }
    }

    @Composable
    private fun Base() {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Column {
                Menu()
                //MostrarGrupos()
                Botones()
            }

        }
    }

    @Composable
    private fun Menu() {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Purple40),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Chat")
            }
            Button(
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Grupos", color = Pink80)
            }
            Button(
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Tareas")
            }
        }
    }

    @Composable
    private fun Botones() {
        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text("Crear Grupo", color = Color.White)
            }
            Button(onClick = { /*TODO*/ }) {
                Text("Unirse a un Grupo", color = Color.White)
            }
        }
    }

}
