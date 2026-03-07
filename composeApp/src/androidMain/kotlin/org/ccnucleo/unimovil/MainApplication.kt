package org.ccnucleo.unimovil
// composeApp/src/androidMain/kotlin/org/ccnucleo/unimovil/android/MainApplication.kt

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import com.liftric.kvault.KVault
import org.ccnucleo.unimovil.di.networkModule

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // 1. Definimos el módulo de Android aquí
        val androidSpecificModule = module {
            single { KVault(androidContext(), "unimovil_secure_prefs") }
        }

        // 2. Iniciamos Koin globalmente
        startKoin {
            androidLogger() // Ayuda a ver los errores de Koin en el Logcat
            androidContext(this@MainApplication) // Esto es vital
            modules(networkModule, androidSpecificModule)
        }
    }
}