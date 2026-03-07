package org.ccnucleo.unimovil

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import org.ccnucleo.unimovil.data.repository.ScrapingRepository
import org.ccnucleo.unimovil.presentation.features.home.ui.HomeScreen
import org.ccnucleo.unimovil.presentation.features.login.ui.LoginScreen
import org.koin.compose.koinInject

@Composable
fun App() {
    MaterialTheme {
        val repository: ScrapingRepository = koinInject()
        // El `remember` es clave aquí para que no se re-evalúe en cada recomposición.
        val hasSession = remember {
            repository.hasSavedSession()
        }

        // El navegador principal que gestiona el flujo Login <-> Home
        Navigator(
            screen = if (hasSession) HomeScreen() else LoginScreen
        )
    }
}