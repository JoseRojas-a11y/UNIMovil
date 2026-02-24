package org.ccnucleo.unimovil.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.parameters

class ScrapingDataSource(private val client: HttpClient) {

    suspend fun loginAndGetData(user: String, pass: String): String {
        // 1. Realizar el Login
        val loginResponse = client.submitForm(
            url = "https://alumnos.uni.edu.pe/login",
            formParameters = parameters {
                append("username", user)
                append("password", pass)
            }
        )

        // 2. Si el login fue exitoso (Ktor ya tiene las cookies), pedimos los datos
        return if (loginResponse.status.value in 200..300) {
            client.get("https://ejemplo.com/dashboard/datos").bodyAsText()
        } else {
            throw Exception("Error de autenticación")
        }
    }
}