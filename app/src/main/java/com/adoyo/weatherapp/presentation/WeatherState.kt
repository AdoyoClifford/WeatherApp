package com.adoyo.weatherapp.presentation

import com.adoyo.weatherapp.domain.weather.WeatherInfo


data class WeatherState (
    val weatherInfo: WeatherInfo? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)