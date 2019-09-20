package com.husar.openweather.di.koin

import androidx.room.Room
import com.husar.openweather.data.local.AppDatabase
import org.koin.dsl.module

private const val DATABASE_NAME = "OPEN"

val databaseModule = module {
    single {
        Room.databaseBuilder(get(),
        AppDatabase::class.java,
        DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDatabase>().openWeatherDao() }
}