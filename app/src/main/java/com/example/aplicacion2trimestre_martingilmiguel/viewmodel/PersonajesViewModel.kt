package com.example.aplicacion2trimestre_martingilmiguel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacion2trimestre_martingilmiguel.model.Personaje
import com.example.aplicacion2trimestre_martingilmiguel.providers.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonajesViewModel : ViewModel() {

    // LiveData que contiene la lista de personajes de la API
    private val _listaPersonajes = MutableLiveData<List<Personaje>>()
    val listaPersonajes: LiveData<List<Personaje>> get() = _listaPersonajes

    // LiveData para manejar los errores y mostrarlos en la interfaz de usuario con un Toast
    //al ser viewModel no puedo poner un toast porque no tengo contexto
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun traerDatos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val respuesta = ApiClient.apiClient.getPersonajes()
                withContext(Dispatchers.Main) {
                    if (respuesta.isSuccessful) {
                        _listaPersonajes.value = respuesta.body()?.listaPersonajes ?: emptyList()
                    } else {
                        _error.value = "Error en la respuesta de la API"
                    }
                }
            } catch (e: Exception) {
                _error.postValue("Error en la carga de datos: ${e.message}")
            }
        }
    }
}
