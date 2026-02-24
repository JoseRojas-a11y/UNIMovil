package org.ccnucleo.unimovil.data.repository

import org.ccnucleo.unimovil.data.local.AuthVault
import org.ccnucleo.unimovil.data.remote.ScrapingDataSource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class ScrapingRepository(
    private val remoteSource: ScrapingDataSource,
    private val localVault: AuthVault
) {

    /**
     * Comprueba si el usuario ya tiene una sesión guardada.
     * Útil para decidir qué pantalla mostrar al inicio (Login o Dashboard).
     */
    fun hasSavedSession(): Boolean {
        return localVault.getCredentials() != null
    }

    /**
     * Intenta extraer los datos usando las credenciales guardadas en la bóveda.
     * Si no hay credenciales o el login falla, devuelve un error.
     */
    suspend fun fetchScrapedDataSilently(): Result<String> = withContext(Dispatchers.IO) {
        val session = localVault.getCredentials()
            ?: return@withContext Result.failure(Exception("No hay credenciales guardadas. Requiere login manual."))

        try {
            // Intentamos loguearnos y hacer scraping en segundo plano
            val data = remoteSource.loginAndGetData(session.username, session.password)
            Result.success(data)
        } catch (e: Exception) {
            // Si la petición falla (ej. contraseña cambiada remotamente), limpiamos la bóveda por seguridad
            localVault.clearCredentials()
            Result.failure(Exception("Error de autenticación o scraping: ${e.message}"))
        }
    }

    /**
     * Flujo manual: El usuario introduce sus datos.
     * Si el scraping es exitoso, guardamos las credenciales en la bóveda de forma segura.
     */
    suspend fun loginAndScrape(user: String, pass: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val data = remoteSource.loginAndGetData(user, pass)

            // ¡Éxito! Guardamos las credenciales para la próxima vez
            localVault.saveCredentials(user, pass)

            Result.success(data)
        } catch (e: Exception) {
            Result.failure(Exception("Credenciales incorrectas o error de red: ${e.message}"))
        }
    }

    /**
     * Cierra sesión eliminando las credenciales locales.
     */
    fun logout() {
        localVault.clearCredentials()
    }
}