package com.example.jetweatherapp.model

data class CurrentWeather(
    val base: String?,
    val clouds: Clouds?,
    val cod: Int?,
    val coord: Coord?,
    val dt: Int?,
    val id: Int?,
    val main: Main?,
    val name: String?,
    val sys: SysX?,
    val timezone: Int?,
    val visibility: Int?,
    val weather: List<Weather>?,
    val wind: Wind?,
    val rain: Rain?,
    val snow: Snow?
)