package com.example.jetweatherapp.model

data class BlogByIdResponse(
    val status: String,
    val message: String,
    val data: Blog // Blog là kiểu dữ liệu của bài viết duy nhất
)
