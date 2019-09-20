package com.husar.openweather.data.local

import androidx.room.*
import com.husar.openweather.data.model.WeatherRecord
import io.reactivex.Single

@Dao
interface OpenWeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWeather(weather: WeatherRecord): Single<Long>

    @Query("SELECT * FROM WeatherRecord")
    fun getAllWeathers(): Single<List<WeatherRecord>>

    @Query("SELECT * FROM WeatherRecord WHERE uuid = :weatherId")
    fun getWeatherById(weatherId: Int?): Single<WeatherRecord>

    @Query("SELECT * FROM WeatherRecord WHERE name = :locationName")
    fun getWeatherByName(locationName: String?): Single<WeatherRecord>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWeather(weather: WeatherRecord): Single<Int>

    @Query("DELETE FROM WeatherRecord")
    fun deleteAll(): Single<Int>

    @Query("DELETE FROM WeatherRecord WHERE name = :locationName")
    fun deleteWeatherByName(locationName: String?)

    @Delete
    fun deleteWeather(weather: WeatherRecord): Single<Int>
}