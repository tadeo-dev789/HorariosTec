package com.example.horariostec

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.horariostec.scrapping.Materia

class SharedViewModel: ViewModel() {

    //Lista mutable de materias seleccionadas
    private val _materiasSeleccionadas = MutableLiveData<MutableList<Materia>>(mutableListOf())
    val materiasSeleccionadas: LiveData<MutableList<Materia>>get() = _materiasSeleccionadas

    var onMessageCallback: ((String) -> Unit)? = null

    //Agregar una materia a a lista
    fun agregarMateria(materia: Materia){
        var listaActual = _materiasSeleccionadas.value?: mutableListOf()

        val existe = listaActual.find { it.materia == materia.materia}!= null

        if(!existe){
            listaActual.add(materia)
            _materiasSeleccionadas.value = listaActual
            onMessageCallback?.invoke("Materia agregada exitosamente")

        }else{
            onMessageCallback?.invoke("La materia ya está en la lista")
        }


    }

    //ELimina una materia de la lista
    fun eliminarMateria(materia: Materia){
        var listaActual = _materiasSeleccionadas.value?: mutableListOf()
        listaActual.remove(materia)
        _materiasSeleccionadas.value = listaActual
    }


}