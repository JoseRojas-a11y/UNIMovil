package org.ccnucleo.unimovil.presentation.features.schedule.ui

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ScheduleScreenModel : ScreenModel {
    private val _welcomeMessage = MutableStateFlow("Bienvenido al cronograma")
    val welcomeMessage: StateFlow<String> = _welcomeMessage

}