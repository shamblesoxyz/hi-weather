package com.example.jetweatherapp.network

import com.example.jetweatherapp.model.Blog
import com.example.jetweatherapp.model.BlogByIdResponse
import com.example.jetweatherapp.model.BlogResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BlogApiService {
    @GET("api/blogs")
    suspend fun getBlogs(): BlogResponse

    @GET("api/blogs/{id}")
    suspend fun getBlogById(@Path("id") blogId: String): BlogByIdResponse
}
