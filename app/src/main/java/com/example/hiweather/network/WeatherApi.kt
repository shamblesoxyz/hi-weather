package com.example.hiweather.network

import com.example.hiweather.model.WeatherData
import com.example.hiweather.utils.Constants
import com.example.hiweather.model.CurrentWeather
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = Constants.API_KEY
    ): CurrentWeather

    @GET("data/2.5/forecast")
    suspend fun get5Day3HourWeatherForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = Constants.API_KEY
    ): WeatherData
}