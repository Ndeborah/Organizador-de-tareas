package ar.edu.unlam.organizador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme

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
    fun Base() {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Greeting(name = "Android", modifier = Modifier.padding(Dp(20F)))
                Greeting(name = "Android", modifier = Modifier.padding(Dp(20F)))
            }

        }
    }

    //El primer parámetro opcional es el Modifier por eso figura como "modifier=", porque podés o no darle un valor.
    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hola $name",
            modifier = modifier,
        )

    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Base()
    }


}
