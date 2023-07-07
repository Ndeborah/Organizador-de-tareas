package ar.edu.unlam.organizador.ui.componentes

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toLowerCase
import ar.edu.unlam.organizador.ChatDeGrupoActivity
import ar.edu.unlam.organizador.ListaDeGruposActivity
import ar.edu.unlam.organizador.MainActivity
import ar.edu.unlam.organizador.UsuarioActivity
import ar.edu.unlam.organizador.ui.theme.Purple40
import java.util.Locale

@Composable
fun Menu(context: Context, selectedName: String) {
    val chatDeGrupoActivityIntent = Intent(context, ChatDeGrupoActivity::class.java).apply {
        putExtra("nombre", "Grupo 1")
    }

    val mainActivity = Intent(context, MainActivity::class.java)

//    val tareasDeGrupoActivity = Intent(context, TareasDeGrupoActivity::class.java).apply {
//        putExtra("nombre", "Grupo 1")
//    }

    val listaDeGruposActivity = Intent(context, ListaDeGruposActivity::class.java)

    val usuarioActivity = Intent(context, UsuarioActivity::class.java)
    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Borrar Tarea",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            selected = selectedName.lowercase(Locale.ROOT) == "grupos",
            onClick = {
                context.startActivity(listaDeGruposActivity)
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
            selected = selectedName.lowercase(Locale.ROOT) == "tareas",
            onClick = {
                context.startActivity(mainActivity)
            },
            label = {
                Text(
                    "Tareas",
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Borrar Tarea",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            selected = selectedName.lowercase(Locale.ROOT) == "usuario",
            onClick = {
                context.startActivity(usuarioActivity)
            },
            label = {
                Text(
                    "Usuario",
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Abrir chat",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            selected = selectedName.lowercase(Locale.ROOT) == "chat",
            onClick = {
                context.startActivity(chatDeGrupoActivityIntent)
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
