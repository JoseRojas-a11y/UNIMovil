package org.ccnucleo.unimovil.di

import com.liftric.kvault.KVault
import org.koin.dsl.module

val iosModule = module {
    // En iOS no se necesita el contexto
    single { KVault("unimovil_secure_prefs") }
}