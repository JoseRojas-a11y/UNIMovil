package org.ccnucleo.unimovil.presentation.features.schedule.ui

import androidx.lifecycle.get
import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.dsl.module

class ScheduleScreenModel : ScreenModel {
    private val _welcomeMessage = MutableStateFlow("Bienvenido al cronograma")
    val welcomeMessage: StateFlow<String> = _welcomeMessage

}

// --- En tu módulo de Koin (AppModule.kt) ---
val appModule = module {

    // Registras el nuevo ScreenModel.
    // Usamos 'factory' porque su ciclo de vida está atado a la pantalla.
    factory { ScheduleScreenModel() }
}
