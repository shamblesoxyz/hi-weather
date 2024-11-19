package com.example.jetweatherapp.network

import com.example.jetweatherapp.model.Blog
import com.example.jetweatherapp.model.BlogByIdResponse
import com.example.jetweatherapp.model.BlogResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BlogApiService {
    @GET("api/blog")
    suspend fun getBlogs(): BlogResponse

    @GET("api/blog/{id}")
    suspend fun getBlogById(@Path("id") blogId: String): BlogByIdResponse
}