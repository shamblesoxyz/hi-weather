package com.example.hiweather.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.S) WeatherScreens.MainScreen.name else WeatherScreens.SplashScreen.name) {
        composable(route = WeatherScreens.SplashScreen.name) {
            // TODO: SplashScreen(navController)
        }
        composable(route = WeatherScreens.MainScreen.name) {
            // TODO: MainScreen(navController, hiltViewModel<MainScreenViewModel>(), hiltViewModel<PermissionViewModel>(), hiltViewModel<LocationViewModel>())
        }
        composable(route = WeatherScreens.SettingScreen.name) {
            // TODO: SettingScreen(navController, hiltViewModel<SettingScreenViewModel>())
        }
        composable(route = WeatherScreens.AboutScreen.name) {
            // TODO: AboutScreen(navController)
        }
    }
}