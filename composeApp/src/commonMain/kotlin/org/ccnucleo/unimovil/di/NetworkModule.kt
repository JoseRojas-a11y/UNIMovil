package org.ccnucleo.unimovil.di

import org.ccnucleo.unimovil.data.local.AuthVault
import org.ccnucleo.unimovil.data.remote.ScrapingDataSource
import org.ccnucleo.unimovil.data.remote.createScrapingClient
import org.ccnucleo.unimovil.data.repository.ScrapingRepository
import org.ccnucleo.unimovil.presentation.features.login.ui.LoginScreenModel
import org.koin.dsl.module

val networkModule = module {
    single { createScrapingClient() }
    single { AuthVault(get()) }
    single { ScrapingDataSource(get()) }
    single { ScrapingRepository(get(), get()) }
    factory { LoginScreenModel(get()) }
}