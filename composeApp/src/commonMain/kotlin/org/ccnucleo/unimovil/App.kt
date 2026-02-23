package org.ccnucleo.unimovil

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.koin.compose.KoinApplication
import org.ccnucleo.unimovil.di.appModule
import org.ccnucleo.unimovil.presentation.features.login.ui.LoginScreen

@Composable
fun App() {
    // Inicializamos Koin para el contexto de Compose
    KoinApplication(application = {
        modules(appModule)
    }) {
        MaterialTheme {
            // Inicializamos el enrutador de Voyager
            Navigator(LoginScreen())
        }
    }
}