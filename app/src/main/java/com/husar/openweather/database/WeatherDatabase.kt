package com.husar.openweather.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.husar.openweather.model.WeatherResponse
import com.husar.openweather.utility.Converters

@Database(entities = arrayOf(WeatherResponse::class), version = 4, exportSchema = true)
@TypeConverters(Converters::class)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            WeatherDatabase::class.java,
            "weatherdatabase"
        ).fallbackToDestructiveMigration()
            .build()
    }
}