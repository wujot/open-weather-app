package com.husar.openweather

import android.app.Application
import com.husar.openweather.di.koin.databaseModule
import com.husar.openweather.di.koin.networkModule
import com.husar.openweather.di.koin.repositoryModule
import com.husar.openweather.di.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(viewModelModule, networkModule, repositoryModule, databaseModule))
        }
    }
}