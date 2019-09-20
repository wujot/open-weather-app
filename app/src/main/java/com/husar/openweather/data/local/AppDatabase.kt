package com.husar.openweather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.husar.openweather.data.model.WeatherRecord
import com.husar.openweather.model.WeatherResponse
import com.husar.openweather.utility.Converters
import com.husar.openweather.utility.WeatherConverters

@Database(entities = arrayOf(WeatherRecord::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun openWeatherDao(): OpenWeatherDao
}