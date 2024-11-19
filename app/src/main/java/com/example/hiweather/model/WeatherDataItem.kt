package com.example.jetweatherapp.model

data class WeatherDataItem (
    val clouds: Clouds?,
    val dt: Long?,
    val dt_txt: String?,
    val main: Main?,
    val pop: Double?,
    val rain: Rain?,
    val sys: Sys?,
    val visibility: Long?,
    val weather: List<Weather>?,
    val wind: Wind?
)