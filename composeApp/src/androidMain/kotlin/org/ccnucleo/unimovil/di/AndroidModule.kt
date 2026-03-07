package org.ccnucleo.unimovil.di

import com.liftric.kvault.KVault
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    // Aquí proveemos la instancia de KVault con su contexto
    single { KVault(androidContext(), "unimovil_secure_prefs") }
}