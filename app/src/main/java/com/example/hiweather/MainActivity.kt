package com.example.hiweather

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.hiweather.navigation.WeatherNavigation
import com.example.hiweather.repository.WeatherRepository
import com.example.hiweather.ui.theme.HiWeatherAppTheme
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Inject weatherRepository vào MainActivity
    @Inject
    lateinit var weatherRepository: WeatherRepository

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            // Quyền được cấp, thực hiện lấy vị trí và cập nhật widget
            getLocationAndWeather()
        } else {
            // Quyền bị từ chối
            Toast.makeText(this, "Permission denied to access location", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cung cấp khả năng yêu cầu quyền
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Yêu cầu quyền truy cập vị trí
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            // Quyền đã được cấp, thực hiện lấy vị trí và cập nhật widget
            getLocationAndWeather()
        }

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

    private fun getLocationAndWeather() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let { loc ->
                // Gọi phương thức cập nhật widget với tọa độ (latitude, longitude)
                updateWeatherWidget(loc.latitude, loc.longitude)
            }
        }
    }

    private fun updateWeatherWidget(latitude: Double, longitude: Double) {
        // Gọi phương thức cập nhật widget
        CoroutineScope(Dispatchers.IO).launch {
            val result = weatherRepository.getCurrentWeather(latitude, longitude, "metric")
            result.data?.let { weather ->
                // Cập nhật widget với dữ liệu thời tiết ở đây
                // Ví dụ: gửi broadcast tới widget để cập nhật UI
            }
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