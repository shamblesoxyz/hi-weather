package com.example.jetweatherapp.model

import com.google.gson.annotations.SerializedName

data class Snow(
    val all: Int?,
    @SerializedName("1h")
    val oneH: Double?,
    @SerializedName("3h")
    val threeH: Double?
)