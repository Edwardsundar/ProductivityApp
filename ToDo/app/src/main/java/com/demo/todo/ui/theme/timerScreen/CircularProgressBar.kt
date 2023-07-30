package com.demo.todo.ui.theme.timerScreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import com.demo.todo.ui.theme.OrangeYellow1
import com.demo.todo.ui.theme.OrangeYellow2
import com.demo.todo.ui.theme.OrangeYellow3

@Composable
fun CircleProgressBar(
    currentPercentageOfTimer : Float = 0.5f,
    modifier: Modifier = Modifier
){
    Canvas(
        modifier = modifier
    ) {
        val strokeWidth = 35f
        val radius = size.width / 2 - strokeWidth / 2
        drawArc(
            color =  OrangeYellow1,
            style = Stroke(strokeWidth),
            startAngle = -90f,
            sweepAngle = currentPercentageOfTimer * 360,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2, radius * 2)
        )
    }
}