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
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun Menu(controller: NavHostController) {
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Borrar Tarea",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "grupos" } == true,
            onClick = {
                controller.navigate("grupos")
            },
            label = {
                Text(
                    "Grupos",
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Tareas",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "tareas" } == true,
            onClick = {
                controller.navigate("tareas")
            }, label = {
                Text(
                    "Tareas",
                    color = MaterialTheme.colorScheme.primary,
                )
            })
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Borrar Tarea",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "usuario" } == true,
            onClick = {
                controller.navigate("usuario")
            }, label = {
                Text(
                    "Usuario",
                    color = MaterialTheme.colorScheme.primary,
                )
            })
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Abrir chat",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "chats" } == true,
            onClick = {
                controller.navigate("chats")
            },
            label = {
                Text(
                    "Chat",
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        )
    }
}
