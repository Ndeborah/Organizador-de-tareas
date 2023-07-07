package ar.edu.unlam.organizador.ui.componentes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import java.util.Locale

@Composable
fun Menu(selectedName: String, controller: NavHostController) {

    NavigationBar {
        NavigationBarItem(icon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Borrar Tarea",
                tint = MaterialTheme.colorScheme.primary
            )
        }, selected = selectedName.lowercase(Locale.ROOT) == "grupos", onClick = {
            controller.navigate("grupos")
        }, label = {
            Text(
                "Grupos",
                color = MaterialTheme.colorScheme.primary,
            )
        })
        NavigationBarItem(icon = {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Tareas",
                tint = MaterialTheme.colorScheme.primary
            )
        }, selected = selectedName.lowercase(Locale.ROOT) == "tareas", onClick = {
            controller.navigate("tareas")
        }, label = {
            Text(
                "Tareas",
                color = MaterialTheme.colorScheme.primary,
            )
        })
        NavigationBarItem(icon = {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "Borrar Tarea",
                tint = MaterialTheme.colorScheme.primary
            )
        }, selected = selectedName.lowercase(Locale.ROOT) == "usuario", onClick = {
            controller.navigate("usuario")
        }, label = {
            Text(
                "Usuario",
                color = MaterialTheme.colorScheme.primary,
            )
        })
        NavigationBarItem(icon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Abrir chat",
                tint = MaterialTheme.colorScheme.primary
            )
        }, selected = selectedName.lowercase(Locale.ROOT) == "chat", onClick = {
            controller.navigate("chats")
        }, label = {
            Text(
                "Chat",
                color = MaterialTheme.colorScheme.primary,
            )
        })

    }
}
