package ar.edu.unlam.organizador.ui.componentes

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ar.edu.unlam.organizador.ChatDeGrupoActivity
import ar.edu.unlam.organizador.GrupoActivity
import ar.edu.unlam.organizador.MainActivity
import ar.edu.unlam.organizador.TareasDeGrupoActivity
import ar.edu.unlam.organizador.ui.theme.Purple40

@Composable
fun Menu(context: Context) {
    val chatDeGrupoActivityIntent = Intent(context, ChatDeGrupoActivity::class.java).apply {
        putExtra("nombre", "Grupo 1")
    }

    val mainActivity = Intent(context, MainActivity::class.java)

    val grupoActivity = Intent(context, GrupoActivity::class.java).apply {
        putExtra("nombre", "Grupo 1")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Purple40),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Button(
            onClick = {
                context.startActivity(chatDeGrupoActivityIntent)

            }
        ) {
            Text(text = "Chat")
        }
        Button(
            onClick = {
                context.startActivity(mainActivity)
            }
        ) {
            Text(text = "Grupos")
        }
    }
}
