package com.example.jetweatherapp.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jetweatherapp.R

@Composable
fun ResetButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .padding(bottom = 25.dp, end = 15.dp)
            .size(60.dp)
            .shadow(elevation = 5.dp, shape = CircleShape, clip = false),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
        onClick = { onClick() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_reset),
            contentDescription = "reset location icon",
            modifier = Modifier
                .size(32.dp)
        )
    }
}