package com.example.hiweather.model

data class Image (
    val id: String,
    val alt: String,
    val filename: String,
    val mimeType: String,
    val filesize: Int,
    val width: Int,
    val height: Int,
    val focalX: Int,
    val focalY: Int,
    val createdAt: String,
    val updatedAt: String,
    val url: String,
    val thumbnailURL: String?
)