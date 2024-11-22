package com.example.hiweather.screens

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.hiweather.viewmodels.BlogViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun BlogDetailScreen(
    navController: NavHostController,
    blogId: String,
    blogViewModel: BlogViewModel = viewModel()
) {
    // Quan sát LiveData từ ViewModel
    val selectedBlog by blogViewModel.selectedBlog.observeAsState()
    var isLoading by remember { mutableStateOf(true) } // Trạng thái loading

    // Gọi fetchBlogById khi màn hình được hiển thị
    LaunchedEffect(blogId) {
        blogViewModel.fetchBlogById(blogId)
    }

    // Đợi blog dữ liệu được load
    selectedBlog?.let {
        isLoading = false
    }

    // Thêm trạng thái cuộn
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)) {

        Text(
            text = "Blog Detail",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Nếu đang tải dữ liệu
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            // Nếu đã có blog hoặc không có blog
            selectedBlog?.let { blog ->
                // Hiển thị ảnh nếu có
                blog.img?.let {
                    Image(
                        painter = rememberAsyncImagePainter(model = it),
                        contentDescription = "Blog Image",
                        modifier = Modifier.fillMaxWidth().height(250.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Hiển thị tiêu đề
                Text(
                    text = blog.title ?: "No Title",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Hiển thị nội dung blog
                Text(
                    text = blog.content ?: "No Content",
                    fontSize = 16.sp
                )
            } ?: run {
                // Trường hợp không có blog
                Text(text = "Blog not found", fontSize = 16.sp, color = Color.Red)
            }
        }

        // Nút Go Back
        Spacer(modifier = Modifier.weight(1f)) // Để đẩy nút xuống dưới cùng
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Go Back")
        }
    }
}