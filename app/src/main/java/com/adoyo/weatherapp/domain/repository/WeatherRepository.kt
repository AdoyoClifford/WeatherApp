package com.adoyo.weatherapp.domain.repository

import com.adoyo.weatherapp.domain.util.Resource
import com.adoyo.weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}