package com.adoyo.weatherapp.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.adoyo.weatherapp.data.remote.WeatherDataDto
import com.adoyo.weatherapp.data.remote.WeatherDto
import com.adoyo.weatherapp.domain.weather.WeatherData
import com.adoyo.weatherapp.domain.weather.WeatherInfo
import com.adoyo.weatherapp.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


private data class IndexWeatherData(val index: Int, val data: WeatherData)

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperatures = temperatures[index]
        val weatherCodes = weatherCodes[index]
        val pressure = pressures[index]
        val windSpeed = windSpeeds[index]
        val humidity = humidities[index]

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

    }.groupBy { it.index / 24 }.mapValues { it.value.map { it.data } }
}

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}