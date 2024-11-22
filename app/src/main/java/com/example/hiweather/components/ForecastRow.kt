package com.example.jetweatherapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.jetweatherapp.model.WeatherDataItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.format.TextStyle as TimeTextStyle

@Composable
fun ForecastRow(forecast: WeatherDataItem) {
    val painter =
        rememberAsyncImagePainter("https://openweathermap.org/img/wn/${forecast.weather?.get(0)?.icon}.png")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .weight(3.5f),
                text = formatteDate(forecast.dt_txt?.split(" ")?.get(0)!!),
                style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            )
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .weight(1f),
                painter = painter,
                contentDescription = "weather icon",
                contentScale = ContentScale.Fit,
            )
            Text(
                modifier = Modifier
                    .weight(1.5f),
                text = "${forecast.main?.temp_min?.toInt()}° / ${forecast.main?.temp_max?.toInt()}°",
                style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.SemiBold),
                textAlign = TextAlign.End
            )
        }
    }
}

fun formatteDate(dateString: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(dateString, inputFormatter)
    val today = LocalDate.now()
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM")
    val formattedDate = date.format(outputFormatter)
    val day = when (date) {
        today -> "Today"
        today.plusDays(1) -> "Tomorrow"
        else -> {
            date.dayOfWeek.getDisplayName(TimeTextStyle.FULL, Locale.getDefault())
        }
    }
    return "$formattedDate $day"
}
