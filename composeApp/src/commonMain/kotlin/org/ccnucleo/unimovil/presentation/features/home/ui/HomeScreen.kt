package org.ccnucleo.unimovil.presentation.features.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.ccnucleo.unimovil.presentation.components.SharedTopAppBar

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        // Obtenemos el Navigator de Voyager
        val navigator = LocalNavigator.currentOrThrow

        // Inyectamos el ScreenModel usando Koin// Elimina esto:
        val screenModel = getScreenModel<HomeScreenModel>()

        // Observamos el estado
        val message by screenModel.welcomeMessage.collectAsState()

        Scaffold(
            topBar = {
                SharedTopAppBar(title = "Inicio", navigator = navigator)
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = message)
            }
        }
    }
}