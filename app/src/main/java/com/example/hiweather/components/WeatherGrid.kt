package com.example.jetweatherapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherGrid(data: List<List<String>>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0..2) {
                GridCard(modifier = Modifier.weight(1f), i = i, data = data)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 3..5) {
                GridCard(modifier = Modifier.weight(1f), i = i, data = data)
            }
        }
    }
}

@Composable
fun GridCard(modifier: Modifier, i: Int, data: List<List<String>>) {
    Card(
        modifier = modifier.size(100.dp),
        shape = RoundedCornerShape(15.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, top = 5.dp, bottom = 5.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                modifier = Modifier.size(25.dp),
                painter = painterResource(id = data[i][0].toInt()),
                contentDescription = "sun icon"
            )
            Text(text = data[i][1], style = TextStyle(fontSize = 12.sp, color = Color.Gray))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = data[i][2],
                    style = TextStyle(
                        fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                    ),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = data[i][3],
                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                )
            }
        }
    }
}