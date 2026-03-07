package org.ccnucleo.unimovil.data.remote

import io.ktor.client.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.*

fun createScrapingClient(): HttpClient {
    return HttpClient {
        install(HttpCookies) {
            storage = AcceptAllCookiesStorage()
        }

        install(UserAgent) {
            agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15000
            connectTimeoutMillis = 15000
        }
    }
}