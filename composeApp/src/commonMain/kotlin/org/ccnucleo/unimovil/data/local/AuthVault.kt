package org.ccnucleo.unimovil.data.local

import org.ccnucleo.unimovil.domain.models.UserSession
import com.liftric.kvault.KVault

class AuthVault(private val vault: KVault) {
    private val USER_KEY = "secure_user_id"
    private val PASS_KEY = "secure_user_pass"

    // Guarda las credenciales cifradas con AES-256 / Secure Enclave
    fun saveCredentials(user: String, pass: String) {
        vault.set(USER_KEY, user)
        vault.set(PASS_KEY, pass)
    }

    // Recupera las credenciales
    fun getCredentials(): UserSession? {
        val username = vault.string(USER_KEY) ?: return null
        val password = vault.string(PASS_KEY) ?: return null
        return UserSession(username, password)
    }

    // Cierra sesión eliminando las llaves
    fun clearCredentials() {
        vault.deleteObject(USER_KEY)
        vault.deleteObject(PASS_KEY)
    }
}