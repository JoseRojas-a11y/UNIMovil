package org.ccnucleo.unimovil.di

import org.ccnucleo.unimovil.data.remote.createScrapingClient
import org.ccnucleo.unimovil.data.remote.ScrapingDataSource
import org.ccnucleo.unimovil.data.repository.ScrapingRepository
import org.koin.dsl.module
import org.ccnucleo.unimovil.data.local.AuthVault
import org.ccnucleo.unimovil.presentation.features.login.ui.LoginScreenModel

val networkModule = module {
    single { createScrapingClient() }

    // AuthVault confía en que Koin encontrará un KVault (get())
    single { AuthVault(get()) }

    single { ScrapingDataSource(client = get()) }
    single { ScrapingRepository(remoteSource = get(), localVault = get()) }
    factory { LoginScreenModel(get()) }
}