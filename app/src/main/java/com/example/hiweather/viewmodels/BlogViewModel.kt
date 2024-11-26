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
        .baseUrl("http://192.168.143.1:5000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(BlogApiService::class.java)

    init {
        fetchBlogs()
    }

    fun fetchBlogs() {
        viewModelScope.launch {
            try {
                // Giả sử bạn gọi service.getBlogs() để lấy danh sách blogs
                val blogResponse = service.getBlogs() // Lấy dữ liệu từ API
                _blogs.value = blogResponse.data // Cập nhật danh sách blogs vào LiveData
            } catch (e: Exception) {
                e.printStackTrace() // Xử lý lỗi nếu có
            }
        }
    }

    // Lấy blog theo _id
    fun fetchBlogById(blogId: String) {
        viewModelScope.launch {
            try {
                val blogResponse = service.getBlogById(blogId) // Gọi API để lấy blog theo ID
                if (blogResponse.status == "OK") {
                    _selectedBlog.value = blogResponse.data // Cập nhật blog vào LiveData
                    Log.d("Blog", "Fetched blog: ${blogResponse.data}")
                } else {
                    Log.e("Blog", "Failed to fetch blog: ${blogResponse.message}")
                }
            } catch (e: Exception) {
                e.printStackTrace() // Xử lý lỗi nếu có
            }
        }
    }
}