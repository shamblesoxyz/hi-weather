package com.example.hiweather.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.hiweather.viewmodels.BlogViewModel

@Composable
fun BlogScreen(
    navController: NavHostController,
    blogViewModel: BlogViewModel = viewModel()
) {
    // Quan sát LiveData từ ViewModel
    val blogs = blogViewModel.blogs.observeAsState(initial = emptyList())

    // Gọi hàm fetchBlogs khi lần đầu tiên vào màn hình
    LaunchedEffect(Unit) {
        blogViewModel.fetchBlogs()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Blogs",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val blogList = blogs.value ?: emptyList()
            items(count = blogList.size) { index ->
                val blog = blogList[index]
                Log.d("BlogScreen", "Blog ID: ${blog.id}")
                BlogCard(
                    imageRes = "https://hi-weather-website.vercel.app/${blog.image.url}"
                        ?: "https://example.com/default-image.jpg",
                    title = blog.title ?: "No Title",
                    content = blog.content ?: "No Content",
                    onClick = {
                        navController.navigate("BlogDetailScreen/${blog.id}")
                        Log.d("BlogScreen", "Clicked Blog ID: ${blog.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun BlogCard(
    imageRes: String,
    title: String,
    content: String,
    onClick: () -> Unit
) {
    val truncatedContent = content.split(" ").take(6).joinToString(" ") + if (content.split(" ").size > 6) "..." else ""
    val truncatedTitle = title.split(" ").take(12).joinToString(" ") + if (content.split(" ").size > 12) "..." else ""
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = content,
                fontSize = 16.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}