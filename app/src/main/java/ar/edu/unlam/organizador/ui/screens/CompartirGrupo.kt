package ar.edu.unlam.organizador.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CompartirGrupo(idGrupo: String, modifier: Modifier = Modifier) {
    Surface(modifier = modifier) {
        Column {
            Text(text = "Comparte este ID para unirse a tu grupo!")
            SelectionContainer {
                Text(
                    text = idGrupo, fontSize = 30.sp, textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 20.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}