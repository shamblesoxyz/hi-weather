package com.example.hiweather.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiweather.model.Blog
import com.example.hiweather.network.BlogApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class BlogViewModel : ViewModel() {
    // MutableLiveData để thay đổi giá trị
    private val _blogs = MutableLiveData<List<Blog>>()

    // LiveData chỉ đọc, không thể thay đổi giá trị bên ngoài ViewModel
    val blogs: LiveData<List<Blog>> get() = _blogs


    private val _selectedBlog = MutableLiveData<Blog>()
    val selectedBlog: LiveData<Blog> get() = _selectedBlog

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://hi-weather-website.vercel.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(BlogApiService::class.java)

    init {
        fetchBlogs()
    }

    fun fetchBlogs() {
        viewModelScope.launch {
            try {
                val blogResponse = service.getBlogs() // Lấy dữ liệu từ API
                _blogs.value = blogResponse.docs // Cập nhật danh sách blogs vào LiveData
            } catch (e: Exception) {
                e.printStackTrace() // Xử lý lỗi nếu có
            }
        }
    }

    // Lấy blog theo _id
    fun fetchBlogById(blogId: String) {
        viewModelScope.launch {
            try {
                val blogResponse = service.getBlogById(blogId)
                _selectedBlog.value = Blog(
                    id = blogResponse.id,
                    title = blogResponse.title,
                    content = blogResponse.content,
                    image = blogResponse.image,
                    createdAt = blogResponse.createdAt,
                    updatedAt = blogResponse.updatedAt
                )
                Log.d("Blog", "Fetched blog: ${blogResponse.title}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
