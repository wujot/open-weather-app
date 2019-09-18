package com.husar.openweather.data.local

import androidx.room.*
import com.husar.openweather.model.WeatherResponse
import io.reactivex.Single

@Dao
interface OpenWeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWeather(weather: WeatherResponse): Single<Long>

    @Query("SELECT * FROM weather")
    fun getAllWeathers(): Single<List<WeatherResponse>>

    @Query("SELECT * FROM Weather WHERE uuid = :weatherId")
    fun getWeatherById(weatherId: Int?): Single<WeatherResponse>

    @Query("SELECT * FROM Weather WHERE name = :locationName")
    fun getWeatherByName(locationName: String?): Single<WeatherResponse>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWeather(weather: WeatherResponse): Single<Int>

    @Query("DELETE FROM Weather")
    fun deleteAll(): Single<Int>

    @Query("DELETE FROM Weather WHERE name = :locationName")
    fun deleteWeatherByName(locationName: String?)

    @Delete
    fun deleteWeather(weather: WeatherResponse): Single<Int>
}