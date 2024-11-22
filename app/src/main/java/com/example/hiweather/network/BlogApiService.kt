package com.example.hiweather.network

import com.example.hiweather.model.BlogByIdResponse
import com.example.hiweather.model.BlogResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BlogApiService {
    @GET("api/blog")
    suspend fun getBlogs(): BlogResponse

    @GET("api/blog/{id}")
    suspend fun getBlogById(@Path("id") blogId: String): BlogByIdResponse
}