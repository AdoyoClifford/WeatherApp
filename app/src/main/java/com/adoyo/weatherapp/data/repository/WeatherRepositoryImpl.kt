package com.adoyo.weatherapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.adoyo.weatherapp.data.mapper.toWeatherInfo
import com.adoyo.weatherapp.data.remote.WeatherApi
import com.adoyo.weatherapp.domain.repository.WeatherRepository
import com.adoyo.weatherapp.domain.util.Resource
import com.adoyo.weatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                api.getWeatherData(lat, long).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An Unknown Error Ocurred")
        }
    }
}