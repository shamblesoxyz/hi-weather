package com.example.hiweather

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.hiweather.navigation.WeatherNavigation
import com.example.hiweather.ui.theme.HiWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
            CoroutineScope(Dispatchers.IO).launch {
                delay(2000)
                splashScreen.setKeepOnScreenCondition { false }
            }
        }
        setContent {
            WeatherApp()
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun WeatherApp() {
    HiWeatherAppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            WeatherNavigation()
        }
    }
}