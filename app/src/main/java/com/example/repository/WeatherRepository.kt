package com.example.jetweatherapp.repository

import com.example.jetweatherapp.data.DataOrException
import com.example.jetweatherapp.model.CurrentWeather
import com.example.jetweatherapp.model.WeatherData
import com.example.jetweatherapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {

    suspend fun getCurrentWeather(latitude: Double, longitude: Double, temperatureUnit: String): DataOrException<CurrentWeather,Boolean,Exception> {
        val weather = DataOrException<CurrentWeather,Boolean,Exception>()
        try {
            weather.loading = true
            weather.data = weatherApi.getCurrentWeather(latitude,longitude,temperatureUnit)
        } catch (e: Exception) {
            weather.exception = e
        } finally {
            weather.loading = false
        }
        return weather
    }

    suspend fun get5Day3HourWeatherForecast(latitude: Double, longitude: Double, temperatureUnit: String): DataOrException<WeatherData,Boolean,Exception> {
        val forecast = DataOrException<WeatherData,Boolean,Exception>()
        try {
            forecast.loading = true
            forecast.data = weatherApi.get5Day3HourWeatherForecast(latitude,longitude, temperatureUnit)
        } catch (e: Exception) {
            forecast.exception = e
        } finally {
            forecast.loading = false
        }
        return forecast
    }

}