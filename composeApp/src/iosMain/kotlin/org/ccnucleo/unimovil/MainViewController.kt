package org.ccnucleo.unimovil

import androidx.compose.ui.window.ComposeUIViewController
import com.liftric.kvault.KVault
import org.koin.dsl.module
import org.ccnucleo.unimovil.App // Asegúrate de que la ruta coincida con tu paquete

// Esta es la función que Swift llamará para iniciar tu app
fun MainViewController() = ComposeUIViewController {

    // 1. Creamos el módulo específico para iOS
    val iosSpecificModule = module {
        // En iOS, KVault no necesita contexto, solo el nombre del archivo/keychain
        single { KVault("unimovil_secure_prefs") }
    }

    // 2. Le pasamos el módulo a la App compartida
    App()
}