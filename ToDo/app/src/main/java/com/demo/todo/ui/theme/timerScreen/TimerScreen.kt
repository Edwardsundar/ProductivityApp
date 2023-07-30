package com.demo.todo.ui.theme.timerScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.demo.todo.R
import com.demo.todo.resorce.Resorce.Companion.date
import com.demo.todo.ui.theme.BlueViolet1
import com.demo.todo.ui.theme.DeepBlue
import com.demo.todo.ui.theme.OrangeYellow1
import com.demo.todo.ui.theme.TextDarkWite
import com.demo.todo.ui.theme.bottomMenu.BottomMenuItem
import com.demo.todo.ui.theme.bottomMenu.CreateBottomMenu

@Composable
fun TimerScreen(){
    val date by remember {
        mutableStateOf(date)
    }

    var currentPercentageOfTimer by remember {
        mutableFloatStateOf(1f)
    }
    var timeInMinutes by remember {
        mutableStateOf("10.10")
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(50.dp)
                .padding(10.dp),
            contentAlignment = TopEnd
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_options) ,
                contentDescription = "options",
                modifier = Modifier.size(50.dp)

            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Box(modifier = Modifier
            .height(40.dp)
            .width(200.dp)
            .background(
                color = BlueViolet1,
                shape = RoundedCornerShape(20.dp)
            ),
            contentAlignment = Center
            ){
            Text(
                text = date,
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(200.dp))
        Text(
            text = timeInMinutes,
            color = Color.White,
            fontSize = 25.sp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){
            CircleProgressBar(
                modifier = Modifier
                    .fillMaxWidth()
            )
            /*Image(
                painter = if (buttonStateStart) {
                    painterResource(id = R.drawable.ic_start)
                } else {
                    painterResource(id = R.drawable.ic_stop)
                },
                contentDescription = null,
                modifier = Modifier
                    .layoutId("start_stop_button")
                    .clickable {
                        if (!buttonStateStart) {
                            onEvent(HomeScreenEvent.MainTimerStart, 0)
                            buttonStateStart = true
                        } else {
                            onEvent(HomeScreenEvent.MainTimerStop, 0)
                            buttonStateStart = false
                        }
                    }
                    .size(40.dp)
            ) */
        }

        Image(
            painter = painterResource(id = R.drawable.ic_stop),
            contentDescription = null,
            Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.height(200.dp))

        Row(modifier = Modifier
            .height(40.dp)
            .width(200.dp)
            .background(
                color = BlueViolet1,
                shape = RoundedCornerShape(20.dp)
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){

            Image(
                painter = painterResource(
                    id = R.drawable.ic_restart
                ),
                contentDescription = null
            )

            Text(
                text = "Restart",
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}