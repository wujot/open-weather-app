package com.husar.openweather.di.koin

import com.husar.openweather.data.repository.OpenWeatherRepo
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single { OpenWeatherRepo(androidContext(), get(), get()) }
}