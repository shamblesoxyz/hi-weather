package com.example.jetweatherapp.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetweatherapp.model.WeatherData
import com.example.jetweatherapp.screens.getLocalTimeFromUnixTimestamp

@Composable
fun HorizontalForecastListOf24Hours(forecastData: WeatherData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                horizontalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                items(forecastData.list?.subList(0, 10)!!) {
                    UpcomingForecast(
                        iconId = it.weather?.get(0)?.icon!!,
                        time = getLocalTimeFromUnixTimestamp(it.dt.toString()),
                        temperature = it.main?.temp?.toInt().toString()
                    )
                }
            }
        }
    }
}