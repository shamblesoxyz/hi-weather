package com.example.hiweather.model

data class WeatherData(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<WeatherDataItem>?,
    val message: Int?
)