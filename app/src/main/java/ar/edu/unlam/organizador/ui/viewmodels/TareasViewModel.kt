package ar.edu.unlam.organizador.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.edu.unlam.organizador.database.entidades.Tarea
import ar.edu.unlam.organizador.database.repositorios.TareaRepositorio
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class TareasViewModel : ViewModel() {

    val tareas: MutableLiveData<MutableList<Tarea>> = MutableLiveData()

    init {
        // Crea un listener para escuchar los cambios que hay en la base de datos de firebase
        // Se trae la lista y la mapea a una lista de tareas
        // Finalmente actualiza el valor del live data de tareas con el valor mapeado antes
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val newList: MutableList<Tarea> = mutableListOf()
                if (dataSnapshot.exists()) {
                    dataSnapshot.children.forEach { child ->
                        val tarea = Tarea(
                            child.child("id").getValue<String>()!!,
                            child.child("nombre").getValue<String>()!!,
                            child.child("grupo").getValue<String>()!!,
                            child.child("realizada").getValue<Boolean>()!!
                        )
                        newList.add(tarea)
                    }
                    tareas.value = newList
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        TareaRepositorio.listenDb(listener)
    }


    val deleteTarea: (tarea: String) -> Unit = { tarea ->
        TareaRepositorio.deleteByID(tarea)
    }

    // Se trae la tarea del repositorio usando el id de la tarea.
    // Le cambia a la tarea el estado de realizado por el opuesto
    // Actualiza la tarea en el repositorio
    fun switchStatus(tareaID: String) {
        TareaRepositorio.get(tareaID) {
            it.realizada = !it.realizada
            TareaRepositorio.update(it)
        }
    }

}