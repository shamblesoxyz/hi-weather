package com.example.hiweather.model

data class BlogByIdResponse(
    val id: String,
    val title: String,
    val content: String,
    val image: Image,
    val createdAt: String,
    val updatedAt: String
)
