package com.husar.openweather.di

import com.husar.openweather.BuildConfig
import com.husar.openweather.api.WeatherApi
import com.husar.openweather.api.WeatherApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
open class ApiModule {

    @Provides
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    open fun provideWeatherApiService(): WeatherApiService {
        return WeatherApiService()
    }
}