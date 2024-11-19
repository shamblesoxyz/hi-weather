package com.example.jetweatherapp.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.jetweatherapp.widgets.WormIndicator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherViewPager(pagerData: List<String>) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            val (hzPager, workIndicator) = createRefs()
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.constrainAs(hzPager) {
                    top.linkTo(parent.top)
                    bottom.linkTo(workIndicator.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) { page ->
                PageContent(page = page, pagerData[page])
            }
            WormIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .padding(5.dp)
                    .constrainAs(workIndicator) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                count = 3
            )
        }
    }
}

@Composable
fun PageContent(page: Int, data: String) {
    val heading = when (page) {
        0 -> "Tomorrow's Temperature"
        1 -> "Tomorrow's Max and Min Temperature"
        2 -> "Tomorrow's Feels Like Temperature"
        else -> "Not Available"
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = heading,
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = data,
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.SemiBold),
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}