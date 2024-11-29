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
            items(count = blogs.value.size) { index ->
                val blog = blogs.value[index]
//                Log.d("BlogScreen", "Image URL: ${blog.img}").toString()
                Log.d("BlogScreen", "Blog ID: ${blog._id}")
                BlogCard(
                    imageRes = blog.img ?: "https://example.com/default-image.jpg",
                    title = blog.title ?: "No Title",
                    content = blog.content ?: "No Content",
                    onClick = {
//                        navController.navigate("BlogDetailScreen")
                        navController.navigate("BlogDetailScreen/${blog._id}")
//                        navController.navigate("${WeatherScreens.BlogDetailScreen.name}/${blog._id}")
                        Log.d("BlogScreen", "Clicked Blog ID: ${blog._id}")
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
    val truncatedTitle = content.split(" ").take(12).joinToString(" ") + if (content.split(" ").size > 12) "..." else ""
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
                text = truncatedTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = truncatedContent,
                fontSize = 16.sp
            )
        }
    }
}