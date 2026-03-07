package org.ccnucleo.unimovil.data.remote

import com.fleeksoft.ksoup.Ksoup
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*

class ScrapingDataSource(private val client: HttpClient) {

    private val LOGIN_URL = "https://alumnos.uni.edu.pe/login"
    private val COURSES_URL = "https://alumnos.uni.edu.pe/informacion-academica/cursos"

    suspend fun loginAndGetData(user: String, pass: String): String {
        val loginResponse = client.submitForm(
            url = LOGIN_URL,
            formParameters = parameters {
                append("text-codigo", user)
                append("text-password", pass)
            }
        )

        // Verificamos si la petición fue aceptada (Códigos 200 a 299, o 302 si hay redirección)
        if (loginResponse.status.value !in 200..302) {
            throw Exception("El servidor rechazó la conexión.")
        }

        // 2. Navegar a la página protegida (Ktor ya envía la cookie de sesión automáticamente)
        val dataResponse = client.get(COURSES_URL)
        val htmlContent = dataResponse.bodyAsText()

        // 3. Evaluar si el login fue exitoso leyendo el HTML
        if (htmlContent.contains("Contraseña incorrecta", ignoreCase = true)) {
            throw Exception("Credenciales inválidas.")
        }

        // 4. Extraer los datos con Ksoup
        return parseHtmlData(htmlContent)
    }

    private fun parseHtmlData(html: String): String {
        val document = Ksoup.parse(html)

        // ⚠️ Ejemplo: Buscar el elemento que contiene el nombre del alumno
        // Supongamos que en la web dice: <div class="profile-name">Jose Martin</div>
        val studentName = document.select("div.profile-name").first()?.text()

        return studentName ?: "No se encontró el dato en la página."
    }
}