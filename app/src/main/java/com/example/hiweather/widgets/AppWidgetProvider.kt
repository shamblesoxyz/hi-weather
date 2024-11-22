package com.example.hiweather.widgets

import android.Manifest
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.hiweather.MainActivity
import com.example.hiweather.R
import com.example.hiweather.model.WeatherDataItem
import com.example.hiweather.repository.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class WeatherWidgetProvider : AppWidgetProvider() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @Inject
    lateinit var weatherRepository: WeatherRepository // Inject repository trực tiếp

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.widget_layout)

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        // Kiểm tra quyền truy cập vị trí
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Lấy vị trí nếu có quyền
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let { loc ->
                    updateWeatherData(context, appWidgetManager, appWidgetId, views, loc.latitude, loc.longitude)
                }
            }
        } else {
            Log.e("WeatherWidgetProvider", "Permission for location is not granted")
        }

        // Tạo Intent để mở ứng dụng khi nhấn vào widget
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.widget_root, pendingIntent)

        // Cấu hình sự kiện nhấn nút "Reset"
        configureResetButton(context, views)

        // Cập nhật widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun updateWeatherData(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        views: RemoteViews,
        latitude: Double,
        longitude: Double
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val currentWeatherResult = weatherRepository.getCurrentWeather(latitude, longitude, "metric")
                val forecastResult = weatherRepository.get5Day3HourWeatherForecast(latitude, longitude, "metric")

                currentWeatherResult.data?.let { weather ->
                    // Cập nhật dữ liệu thời tiết hiện tại
                    views.setTextViewText(R.id.widget_location, weather.name)
                    views.setTextViewText(R.id.widget_temperature, "${weather.main?.temp}°")
                    views.setTextViewText(R.id.widget_description, weather.weather?.get(0)?.description ?: "N/A")

                    val iconUrl = "https://openweathermap.org/img/wn/${weather.weather?.get(0)?.icon}.png"
                    val bitmap = loadBitmapFromUrl(iconUrl)
                    bitmap?.let { views.setImageViewBitmap(R.id.icon, it) }

                    // Cập nhật dữ liệu dự báo 5 thời điểm tiếp theo
                    forecastResult.data?.list?.take(5)?.forEachIndexed { index, forecast ->
                        updateForecastData(context, views, index, forecast)
                    }
                }

                // Cập nhật widget
                appWidgetManager.updateAppWidget(appWidgetId, views)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("WeatherWidgetProvider", "Error updating weather data: ${e.message}")
            }
        }
    }

    private fun updateForecastData(context: Context, views: RemoteViews, index: Int, forecast: WeatherDataItem) {
        val time = forecast.dt_txt?.let { formatTimeToHHMM(it) }
        val temp = "${forecast.main?.temp}°"
        val icon = forecast.weather?.get(0)?.icon ?: "01d"

        // Lấy ID của các view dự báo
        val timeViewId = context.resources.getIdentifier("forecast_time_${index + 1}", "id", context.packageName)
        val iconViewId = context.resources.getIdentifier("forecast_icon_${index + 1}", "id", context.packageName)
        val tempViewId = context.resources.getIdentifier("forecast_temp_${index + 1}", "id", context.packageName)

        // Cập nhật thời gian và nhiệt độ
        views.setTextViewText(timeViewId, time)
        views.setTextViewText(tempViewId, temp)

        // Tải icon thời tiết
        val iconUrl = "https://openweathermap.org/img/wn/${icon}.png"
        Log.d("WeatherWidgetProvider", "Icon URL: $iconUrl")
        val bitmap = loadBitmapFromUrl(iconUrl)
        bitmap?.let { views.setImageViewBitmap(iconViewId, it) }
    }

    private fun configureResetButton(context: Context, views: RemoteViews) {
        val resetIntent = Intent(context, WeatherWidgetProvider::class.java).apply {
            action = "com.example.jetweatherapp.widget.ACTION_RESET"
        }
        val resetPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            resetIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.reset_button, resetPendingIntent)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if (intent.action == "com.example.jetweatherapp.widget.ACTION_RESET") {
            Log.d("WeatherWidgetProvider", "ACTION_RESET received")
            Toast.makeText(context, "Weather is up to date", Toast.LENGTH_SHORT).show()

            // Cập nhật lại widget
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context, WeatherWidgetProvider::class.java))
            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId)
            }
        }
    }

    private fun loadBitmapFromUrl(url: String): Bitmap? {
        return try {
            val inputStream = URL(url).openStream()
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun formatTimeToHHMM(timeString: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val targetFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = originalFormat.parse(timeString)
        return date?.let { targetFormat.format(it) } ?: "N/A"
    }
}
