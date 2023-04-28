package com.adoyo.weatherapp.data.mapper

import android.os.Build
import com.adoyo.weatherapp.data.remote.WeatherDataDto
import com.adoyo.weatherapp.domain.weather.WeatherData
import com.adoyo.weatherapp.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


private data class IndexWeatherData(val index: Int, val data: WeatherData)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperatures = temperatures[index]
        val weatherCodes = weatherCodes[index]
        val pressure = pressures[index]
        val windSpeed = windSpeeds[index]
        val humidity = humidities[index]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            IndexWeatherData(
                index = index, data = WeatherData(
                    time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                    temperatureCelsius = temperatures,
                    pressure = pressure,
                    windSpeed = windSpeed,
                    humidity = humidity,
                    weatherType = WeatherType.fromWMO(weatherCodes)
                )
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }.groupBy { it.index / 24 }.mapValues { it.value.map { it.data } }
}