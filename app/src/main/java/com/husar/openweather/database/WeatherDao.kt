package com.husar.openweather.database

import android.provider.SyncStateContract.Helpers.insert
import android.provider.SyncStateContract.Helpers.update
import androidx.room.*
import com.husar.openweather.model.WeatherResponse

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWeather(weather: WeatherResponse): Long

    @Query("SELECT * FROM weather")
    suspend fun getAllWeathers(): List<WeatherResponse>

    @Query("SELECT * FROM Weather WHERE uuid = :weatherId")
    suspend fun getWeatherById(weatherId: Int?): WeatherResponse

    @Query("SELECT * FROM Weather WHERE name = :locationName")
    suspend fun getWeatherByName(locationName: String?): WeatherResponse

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWeather(weather: WeatherResponse)

    @Query("DELETE FROM Weather")
    suspend fun deleteAll()

    @Query("DELETE FROM Weather WHERE name = :locationName")
    suspend fun deleteWeatherByName(locationName: String?)

    @Delete
    suspend fun deleteWeather(weather: WeatherResponse)
}