package com.demo.todo.calander

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.demo.todo.roomDataBase.ProgressData
import com.demo.todo.state.ScreenState
import com.demo.todo.ui.theme.BlueWithe
import com.demo.todo.ui.theme.LightBron5
import com.demo.todo.ui.theme.LightGreen2
import com.demo.todo.ui.theme.LightGreen3
import com.demo.todo.ui.theme.LightPink2
import com.demo.todo.ui.theme.blueGray
import com.demo.todo.ui.theme.brightBlue
import com.demo.todo.ui.theme.darkGray
import com.demo.todo.ui.theme.gray
import com.demo.todo.ui.theme.green
import com.demo.todo.ui.theme.orange
import com.demo.todo.ui.theme.purple
import com.demo.todo.ui.theme.redOrange
import com.demo.todo.ui.theme.white
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun Graph(
    data: List<ProgressData>
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.9f)),
        contentAlignment = Alignment.Center
    ){
        LazyColumn (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            item {
                TopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(Color.Transparent)
                ) {

                }
                BarChart(
                    data = data,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp , top = 50.dp),
                    colorLis = listOf(
                        orange, brightBlue, green, purple, blueGray, redOrange, darkGray
                    ),
                )
            }
        }
    }
}
@Composable
fun BarChart(
    data: List<ProgressData>,
    modifier: Modifier = Modifier,
    colorLis : List<Color>
){
    var showDescription by remember {
        mutableStateOf(false)
    }
    var colorIndex by remember {
        mutableIntStateOf(0)
    }
    Row(
        modifier = modifier
            .pointerInput(Unit){
                               detectTapGestures (
                                   onDoubleTap = {
                                       showDescription = !showDescription
                                   }
                               )
            },
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            items(data.size) {index->
                var percentage = (1f -data[index].progressPercentage)
                if (percentage < 0.01f) percentage = 0.01f
                Bar(
                    modifier = Modifier
                        .height(120.dp * percentage *data.size)
                        .width(40.dp)
                        .padding(5.dp),
                    primaryColor = colorLis[ index % colorLis.size],
                    percentage = percentage,
                    description = data[index].key,
                    showDescription = showDescription
                )
            }
        }
    }
}

@Composable
fun Bar(
    modifier: Modifier = Modifier,
    primaryColor : Color ,
    percentage : Float ,
    description : String,
    showDescription : Boolean = false
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Canvas(
            modifier = Modifier.fillMaxSize()
        ){
            val width = size.width
            val height = size.height
            val barWidth = width/5*3
            val barHeight = height/8*7
            val barHeight3DPart = height-barHeight
            val barWidth3DPart = (width-barWidth)*(height*0.002f)

            var path = Path().apply {
                moveTo(0f,height)
                lineTo(barWidth,height)
                lineTo(barWidth,height-barHeight)
                lineTo(0f,height-barHeight)
                close()
            }
            drawPath(
                path,
                brush = Brush.linearGradient(
                    colors = listOf(
                        gray,
                        primaryColor
                    )
                )
            )
            path = Path().apply {
                moveTo(barWidth,height-barHeight)
                lineTo(barWidth3DPart+barWidth,0f)
                lineTo(barWidth3DPart+barWidth,barHeight)
                lineTo(barWidth,height)
                close()
            }
            drawPath(
                path,
                brush = Brush.linearGradient(
                    colors = listOf(
                        primaryColor,
                        gray
                    )
                )
            )
            path = Path().apply {
                moveTo(0f,barHeight3DPart)
                lineTo(barWidth,barHeight3DPart)
                lineTo(barWidth+barWidth3DPart,0f)
                lineTo(barWidth3DPart,0f)
                close()
            }
            drawPath(
                path,
                brush = Brush.linearGradient(
                    colors = listOf(
                        gray , primaryColor
                    )
                )
            )
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "${(percentage * 100f).roundToInt()} %",
                    barWidth/5f,
                    height+55f,
                    android.graphics.Paint().apply {
                        color = white.toArgb()
                        textSize = 11.dp.toPx()
                        isFakeBoldText = true
                    }
                )
            }
            if (showDescription){
                drawContext.canvas.nativeCanvas.apply {
                    rotate(-70f , pivot = Offset(barWidth3DPart-20,0f)){
                        drawText(
                            description,
                            0f,
                            0f,
                            android.graphics.Paint().apply {
                                color = white.toArgb()
                                textSize = 14.dp.toPx()
                                isFakeBoldText = true
                            }
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    radius : Float = 500f,
    innerRadius:Float = 250f,
    transparentWidth :Float = 70f,
    data: List<ProgressData>,
    centerText:String = "Progress",

){


    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var isCenterTab by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {

                }
        ){
            val width = size.width
            val height = size.height
            circleCenter = Offset(x = width/2f , y = height/2f)

            var totalValue = 0f
            data.forEach {
                totalValue +=(1- it.progressPercentage)
            }

            val anglePerValue = 360f/totalValue

            var currentStartingAngle = 0f

            data.forEach {
                val scale = 1.0f
                val angleToDraw =(1f-it.progressPercentage) * anglePerValue
                val random = Random(System.currentTimeMillis())
                val red = random.nextInt(256)
                val green = random.nextInt(256)
                val blue = random.nextInt(256)

                val color = Color(red, green, blue)
                scale(scale){
                    drawArc(
                        color = color,
                        startAngle = currentStartingAngle,
                        sweepAngle = angleToDraw,
                        useCenter = true,
                        size = Size(
                            width = radius * 2f,
                            height = radius * 2f
                        ),
                        topLeft = Offset(
                            (width-radius*2f)/2f,
                            (height-radius*2f)/2f
                        )
                    )
                    currentStartingAngle += angleToDraw
                }
            }
        }
    }
}
