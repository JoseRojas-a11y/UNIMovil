package org.ccnucleo.unimovil.presentation.features.login.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginScreenModel : ScreenModel {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    fun onEmailChange(newEmail: String) {
        email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    fun login() {
        screenModelScope.launch {
            _isLoading.value = true

            // Aquí iría tu llamada al repositorio/API real
            delay(1500) // Simulamos una carga de red

            // Validación básica de ejemplo
            if (email.value.isNotBlank() && password.value.isNotBlank()) {
                _loginSuccess.value = true
            }

            _isLoading.value = false
        }
    }
}