package org.ccnucleo.unimovil.presentation.features.login.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.flow.collectLatest
import org.ccnucleo.unimovil.di.appModule
import org.ccnucleo.unimovil.di.networkModule
import org.ccnucleo.unimovil.presentation.features.home.ui.HomeScreen
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

object LoginScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<LoginScreenModel>()

        // 1. Recolección de estados (StateFlow)
        val uiState by screenModel.uiState.collectAsState()
        val codUser by screenModel.codUser.collectAsState()
        val password by screenModel.password.collectAsState()
        val loginEnable by screenModel.isLoginEnable.collectAsState()


        // 2. Efectos secundarios (Navegación)
        LaunchedEffect(Unit) {
            screenModel.navigationEvent.collectLatest {
                unloadKoinModules(networkModule)
                loadKoinModules(appModule)
                navigator.replaceAll(HomeScreen())
            }
        }

        // 3. Estructura visual principal modularizada
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            InitialBlock()

            Spacer(modifier = Modifier.height(60.dp))

            UserBlock(
                username = codUser,
                isError = uiState.errorMessage != null
            ) { screenModel.onLoginChange(it, password) }

            Spacer(modifier = Modifier.height(8.dp))

            PasswordBlock(
                password = password,
                isError = uiState.errorMessage != null
            ) { screenModel.onLoginChange(codUser, it) }

            Spacer(modifier = Modifier.height(16.dp))

            LoginButton(
                loginEnable = loginEnable,
                isLoading = uiState.isLoading
            ) {
                screenModel.login(codUser, password)
            }

            // Manejo del mensaje de error
            uiState.errorMessage?.let { errorMsg ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = errorMsg, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun InitialBlock(modifier: Modifier = Modifier) {
    Text(
        text = "Bienvenido a UniMovil",
        style = MaterialTheme.typography.headlineMedium,
    )
}

@Composable
fun UserBlock(username: String, isError: Boolean, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        value = username,
        onValueChange = { onTextChanged(it) },
        label = { Text("Escribe tu usuario") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        isError = isError,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun PasswordBlock(password: String, isError: Boolean, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        label = { Text("Escribe tu contraseña") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
        isError = isError,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun LoginButton(loginEnable: Boolean, isLoading: Boolean, onLoginSelected: () -> Unit) {
    if (isLoading) {
        CircularProgressIndicator()
    } else {
        Button(
            onClick = { onLoginSelected() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White
            ),
            enabled = loginEnable
        ) {
            Text("Entrar")
        }
    }
}