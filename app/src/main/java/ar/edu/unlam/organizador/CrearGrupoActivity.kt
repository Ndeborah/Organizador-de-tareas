package ar.edu.unlam.organizador

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ar.edu.unlam.organizador.database.entidades.Grupo
import ar.edu.unlam.organizador.ui.theme.OrganizadorTheme
import com.google.firebase.database.FirebaseDatabase

class CrearGrupoActivity : ComponentActivity() {
    private lateinit var nuevoGrupo: Grupo
    var nombre: String = ""
    var contrasenia = ""
    var grupoId = 0
    var database = FirebaseDatabase.getInstance().reference.child("Grupos")

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
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Column {
                CrearGrupo()
            }
        }
    }

    @Composable
    private fun CrearGrupo() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Column() {
                Text(text = "Crear Grupo")
            }
        }
        Spacer(modifier = Modifier.size(100.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Column {
                IngresarNombre().toString()
            }
        }
        Spacer(modifier = Modifier.size(100.dp))
        Column() {
            Button(onClick = {
                /*grupoId++
                nuevoGrupo = Grupo(nombre, contrasenia)
                database.push().setValue(nuevoGrupo)


                /*val grupoMap = HashMap<String, String>()
                grupoMap["Nombre"] = nombre
                grupoMap["Password"] = contrasenia
                database.push().setValue(grupoMap)
                //database.child(grupoId.toString()).setValue(nombre)
                //nuevoGrupo = Grupo(nombre,  "")*/

                //GrupoRepositorio.agregar(nuevoGrupo)
                */

                irAGrupos()
                finish()
            })
            {
                Text(text = "Crear")
            }
            Button(onClick = { finish() }) {
                Text(text = "Cancelar")
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun IngresarNombre(){
        var text by remember { mutableStateOf(TextFieldValue(""))}
        OutlinedTextField (
            value = text,
            onValueChange = { text = it } ,
            label = { Text(text = "Ingresar el nombre del nuevo Grupo")},
            singleLine = true,
            modifier = Modifier.padding(top = 20.dp)
        )
        nombre = text.text
    }

    private fun irAGrupos() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
