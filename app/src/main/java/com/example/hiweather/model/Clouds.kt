package com.example.hiweather.model

import com.google.gson.annotations.SerializedName

data class Clouds(
    val all: Int?,
    @SerializedName("1h")
    val oneH: Double?,
    @SerializedName("3h")
    val threeH: Double?
)