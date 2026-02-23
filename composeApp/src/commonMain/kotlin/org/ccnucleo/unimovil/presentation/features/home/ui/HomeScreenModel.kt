package org.ccnucleo.unimovil.presentation.features.home.ui

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeScreenModel : ScreenModel {
    // Ejemplo de estado para tu UI
    private val _welcomeMessage = MutableStateFlow("Bienvenido a la pantalla principal")
    val welcomeMessage: StateFlow<String> = _welcomeMessage

    // Aquí irían tus llamadas a casos de uso, repositorios, etc.
}