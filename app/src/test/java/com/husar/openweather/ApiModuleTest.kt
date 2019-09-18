package com.husar.openweather

import com.husar.openweather.api.WeatherApiService

class ApiModuleTest(val mockService: WeatherApiService): ApiModule() {
    override fun provideWeatherApiService(): WeatherApiService {
        return mockService
    }
}