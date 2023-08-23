package com.demo.todo.ui.theme.timerScreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.demo.todo.state.ScreenState
import com.demo.todo.ui.theme.OrangeYellow1
import com.demo.todo.ui.theme.OrangeYellow2
import com.demo.todo.ui.theme.OrangeYellow3
import com.demo.todo.util.ScreenEvents

@Composable
fun CircleProgressBar(
    modifier: Modifier = Modifier ,
    state: ScreenState,
    onEvent : (ScreenEvents)->Unit,
){

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.height(750.dp).padding(20.dp)
    ){

        Canvas(
            modifier = modifier
        ) {
            val strokeWidth = 35f
            val radius = size.width / 2 - strokeWidth / 2
            drawArc(
                color = OrangeYellow1,
                style = Stroke(strokeWidth),
                startAngle = -90f,
                sweepAngle = state.progressPercentage * 360,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2)
            )
        }
    }
}