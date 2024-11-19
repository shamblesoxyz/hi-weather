package com.example.jetweatherapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.jetweatherapp.model.CurrentWeather

@Composable
fun WeatherDescription(currentWeather: CurrentWeather) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "${currentWeather.weather?.get(0)?.main} ${currentWeather.main?.temp_min?.toInt()}°/${currentWeather.main?.temp_max?.toInt()}° Weather Description - ${currentWeather.weather?.get(0)?.description}",
        style = TextStyle(
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            lineHeight = 26.sp,
            fontWeight = FontWeight.SemiBold
        )
    )
}