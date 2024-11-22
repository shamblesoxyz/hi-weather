package com.example.hiweather.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hiweather.screens.BlogDetailScreen
import com.example.hiweather.screens.BlogScreen
import com.example.hiweather.screens.MainScreen
import com.example.hiweather.screens.SettingScreen
import com.example.hiweather.screens.SplashScreen
import com.example.hiweather.viewmodels.LocationViewModel
import com.example.hiweather.viewmodels.MainScreenViewModel
import com.example.hiweather.viewmodels.PermissionViewModel
import com.example.hiweather.viewmodels.SettingScreenViewModel
import com.example.hiweather.screens.AboutScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.S) WeatherScreens.MainScreen.name else WeatherScreens.SplashScreen.name) {
        composable(route = WeatherScreens.SplashScreen.name) {
            SplashScreen(navController)
        }
        composable(route = WeatherScreens.MainScreen.name) {
            MainScreen(
                navController = navController,
                mainScreenViewModel = hiltViewModel<MainScreenViewModel>(),
                permissionViewModel = hiltViewModel<PermissionViewModel>(),
                locationViewModel = hiltViewModel<LocationViewModel>()
            )
        }
        composable(route = WeatherScreens.SettingScreen.name) {
            SettingScreen(navController, hiltViewModel<SettingScreenViewModel>())
        }
        composable(route = WeatherScreens.AboutScreen.name) {
            AboutScreen(navController)
        }
        composable(route = WeatherScreens.BlogScreen.name) {
            BlogScreen(navController)
        }
        // Thêm BlogDetailScreen
        composable(route = "BlogDetailScreen/{blogId}") { backStackEntry ->
            val blogId = backStackEntry.arguments?.getString("blogId") ?: ""
            BlogDetailScreen(navController = navController, blogId = blogId)
        }
    }
}