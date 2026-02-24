package org.ccnucleo.unimovil.presentation.features.login.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ccnucleo.unimovil.data.repository.ScrapingRepository

data class LoginState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class LoginScreenModel(
    private val scrapingRepository: ScrapingRepository
) : ScreenModel {
    private val _codUser = MutableStateFlow("")
    val codUser = _codUser.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isLoginEnable = MutableStateFlow(false)
    val isLoginEnable = _isLoginEnable.asStateFlow()


    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    // Usamos Channel para eventos únicos (navegación, mostrar un Toast, etc.)
    private val _navigationEvent = Channel<Unit>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onLoginChange(coduser: String, password: String) {
        _codUser.value = coduser
        _password.value = password
        _isLoginEnable.value = enableCodUser(coduser) && enablePassword(password)
    }

    private fun enableCodUser (coduser: String) : Boolean{
        return (coduser.length == 9) && (coduser.substring(0,7).all { it.isDigit() }) && (coduser[8].isLetter())
    }

    private fun enablePassword (password: String) : Boolean{
        return (password.length >= 6)
    }


    init {
        // Al instanciar la pantalla, verificamos si ya hay sesión previa
        if (scrapingRepository.hasSavedSession()) {
            attemptAutoLogin()
        }
    }

    private fun attemptAutoLogin() {
        screenModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = scrapingRepository.fetchScrapedDataSilently()

            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false) }
                    _navigationEvent.send(Unit)
                },
                onFailure = {
                    // Si el auto-login falla (ej. credenciales cambiadas),
                    // simplemente quitamos la carga y dejamos que el usuario inicie manualmente.
                    _uiState.update { it.copy(isLoading = false, errorMessage = null) }
                }
            )
        }
    }

    fun login(user: String, pass: String) {
        // Validación local rápida
        if (user.isBlank() || pass.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Usuario y contraseña no pueden estar vacíos.") }
            return
        }

        screenModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = scrapingRepository.loginAndScrape(user, pass)

            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false) }
                    // ¡Éxito! Disparamos el evento de navegación
                    _navigationEvent.send(Unit)
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
            )
        }
    }
}