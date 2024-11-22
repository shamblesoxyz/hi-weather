package com.example.hiweather.model

data class BlogResponse(
    val status: String,
    val message: String,
    val data: List<Blog> // List of Blog objects
)
