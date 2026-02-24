package org.ccnucleo.unimovil.domain.models

/**
 * Representa las credenciales del usuario necesarias para mantener
 * la sesión activa durante el proceso de Web Scraping.
 */
data class UserSession(
    val username: String,
    val password: String
)
