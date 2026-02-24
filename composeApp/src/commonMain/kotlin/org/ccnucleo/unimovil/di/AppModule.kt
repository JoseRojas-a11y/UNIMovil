package org.ccnucleo.unimovil.di

import org.koin.dsl.module
import org.ccnucleo.unimovil.presentation.features.home.ui.HomeScreenModel
import org.ccnucleo.unimovil.presentation.features.schedule.ui.ScheduleScreenModel
import org.ccnucleo.unimovil.presentation.features.login.ui.LoginScreenModel

val appModule = module {
    factory { HomeScreenModel() }
    factory { ScheduleScreenModel() }
}