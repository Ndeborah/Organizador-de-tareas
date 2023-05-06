package ar.edu.unlam.organizador

import android.content.Intent
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

class TareasActivity : ComponentActivity() {
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
                onClick = {
                    irAChat()
                    onStop()
                }
            ) {
                Text(text = "Chat")
            }
            Button(
                onClick = {
                    irAGrupos()
                    onStop()
                }
            ) {
                Text(text = "Grupos")
            }
            Button(
                onClick = {}
            ) {
                Text(text = "Tareas", color = Pink80)
            }
        }
    }

    private fun irAChat() {
        val intent = Intent(this, ChatActivity::class.java)
        startActivity(intent)
    }

    private fun irAGrupos() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}