package com.example.jetweatherapp.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetweatherapp.model.WeatherData
import com.example.jetweatherapp.screens.getDistinctDateData

@Composable
fun ForecastCard(forecastData: WeatherData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(290.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
            verticalArrangement = Arrangement.Center
        ) {
            items(getDistinctDateData(forecastData.list!!)) {
                ForecastRow(forecast = it)
            }
        }
    }
}