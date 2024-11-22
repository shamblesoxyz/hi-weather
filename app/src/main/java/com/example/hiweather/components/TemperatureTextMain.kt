package com.example.jetweatherapp.components

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.jetweatherapp.model.CurrentWeather

@Composable
fun TemperatureTextMain(currentWeather: CurrentWeather) {
    TemperatureText(
        modifier = Modifier.padding(top = 40.dp), style1 = TextStyle(
            fontSize = 100.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Default
        ), style2 = TextStyle(
            fontSize = 80.sp, fontWeight = FontWeight.Bold
        ), temperature = currentWeather.main?.temp?.toInt()!!
    )
}

@Composable
fun TemperatureText(
    modifier: Modifier = Modifier,
    modifier1: Modifier = Modifier,
    modifier2: Modifier = Modifier,
    style1: TextStyle = TextStyle(),
    style2: TextStyle = TextStyle(),
    temperature: Int = 0,
) {
    ConstraintLayout(
        modifier = modifier,
    ) {
        val (tvTemp, tvUnit) = createRefs()
        Text(
            modifier = modifier1
                .constrainAs(tvTemp) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            text = "$temperature",
            style = style1,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = modifier2
                .constrainAs(tvUnit) {
                    start.linkTo(tvTemp.end)
                    top.linkTo(tvTemp.top)
                }
                .offset(y = 0.dp),
            text = "°",
            style = style2,
            textAlign = TextAlign.Center
        )
    }
}