package com.husar.openweather

import com.husar.openweather.api.WeatherApiService
import com.husar.openweather.di.ApiModule

class ApiModuleTest(val mockService: WeatherApiService): ApiModule() {
    override fun provideWeatherApiService(): WeatherApiService {
        return mockService
    }
}