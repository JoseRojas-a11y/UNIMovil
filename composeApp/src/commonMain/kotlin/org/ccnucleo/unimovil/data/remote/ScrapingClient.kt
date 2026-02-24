package org.ccnucleo.unimovil.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.UserAgent

fun createScrapingClient() = HttpClient {
    install(HttpCookies) // Mantiene la sesión activa automáticamente
    install(Logging) { level = LogLevel.INFO }

    install(UserAgent) {
        agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36..."
    }
}