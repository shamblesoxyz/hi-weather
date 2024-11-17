package com.example.hiweather.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
private fun Modifier.wormTransition(
    pagerState: PagerState,
    spacing: Dp = 10.dp
) =
    drawBehind {
        val indicatorSize = size.width
        val distance = indicatorSize + spacing.toPx()
        val scrollPosition = pagerState.currentPage + pagerState.currentPageOffsetFraction
        val wormOffset = (scrollPosition % 1) * 2

        // Adjust the xPos by half of the spacing to center the worm properly
        val xPos = (scrollPosition.toInt() * distance) + 4.dp.toPx()
        val head = xPos + distance * 0f.coerceAtLeast(wormOffset - 1)
        val tail = xPos + indicatorSize + 1f.coerceAtMost(wormOffset) * distance - 1

        val worm = RoundRect(
            head, 0f, tail, size.height,
            CornerRadius(50f)
        )

        val path = Path().apply { addRoundRect(worm) }
        drawPath(path = path, color = Color.White)
    }


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WormIndicator(
    count: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    spacing: Dp = 10.dp,
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            modifier = modifier
                .height(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(count) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        )
                )
            }        }

        Box(
            Modifier
                .wormTransition(pagerState, spacing)
                .size(7.dp)
        )
    }
}