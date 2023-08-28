package com.demo.todo.ui.theme.timerScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.todo.MyJava.myJava
import com.demo.todo.R
import com.demo.todo.resorce.Resorce
import com.demo.todo.state.ScreenState
import com.demo.todo.ui.theme.BlueViolet1
import com.demo.todo.util.ScreenEvents
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun TimerScreen(
    state: ScreenState,
    onEvent: (ScreenEvents)->Unit
){
    var date by remember {
        mutableStateOf(Resorce.getTodayDate())
    }
    var buttonStateStart by remember {
        mutableStateOf(false)
    }
    var lastClick by remember {
        mutableLongStateOf(0L)
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
                painter = painterResource(id = R.drawable.ic_options),
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
            )
            .clickable {
                   date = "\uD83D\uDE01"
            },
            contentAlignment = Center
            ){
            Text(
                text = date,
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(185.dp))
        Text(
            text = state.stringTime,
            color = Color.White,
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.height(30.dp))
        Image(
                painter = if (buttonStateStart) {
                    painterResource(id = R.drawable.ic_start)
                } else {
                    painterResource(id = R.drawable.ic_stop)
                },
        contentDescription = null,
        modifier = Modifier
            .layoutId("start_stop_button")
            .clickable {
                val current = System.currentTimeMillis()
                if (current - lastClick >= 1000L){
                    buttonStateStart = !buttonStateStart
                    when (state.timerIsOn) {
                        true -> onEvent(ScreenEvents.TimerStop)
                        false -> onEvent(ScreenEvents.TimerStart)
                    }
                    lastClick = current
                }
            }
            .size(40.dp)
        )

        Spacer(modifier = Modifier.height(200.dp))

        Row(
            modifier = Modifier
            .height(40.dp)
            .width(200.dp)
            .background(
                color = BlueViolet1,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                       onEvent(ScreenEvents.TimerRestart)
            },
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
    CircleProgressBar(
        modifier = Modifier
            .fillMaxWidth(),
        state = state,
        onEvent = onEvent
    )
}