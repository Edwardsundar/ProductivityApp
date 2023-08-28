package com.demo.todo.calander

import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHostState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.size
import androidx.navigation.NavController
import com.demo.todo.R
import com.demo.todo.navcontroller.NavRoot
import com.demo.todo.resorce.Resorce
import com.demo.todo.resorce.TaskPriority
import com.demo.todo.roomDataBase.ProgressData
import com.demo.todo.ui.theme.BlueWithe
import com.demo.todo.ui.theme.CalenderBackGround
import com.demo.todo.util.ScreenEvents
import kotlinx.coroutines.launch

@Composable
fun CalendarView(
    modifier: Modifier = Modifier ,
    data: List<ProgressData>,
    onEvent:(ScreenEvents)->Unit,
    navController:NavController
){
    var pickedDate by remember {
        mutableStateOf(Resorce.getTodayDate())
    }
    var size by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CalenderBackGround),
        verticalArrangement = Arrangement.Center
    ){
        Box(
            modifier = Modifier
                .padding(10.dp)
                .width(150.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(BlueWithe)
                .clickable {
                    navController.navigate(NavRoot.GraphViewNav.root)
                }
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ){
            Text(text = "View All Data")
        }
        AndroidView(
            factory = { CalendarView(it) } ,
            update = {
                it.setOnDateChangeListener { calendarView, year, m, day ->
                    var month :String = (m+1).toString()
                    if (m<10) month = "0${m+1}"
                    pickedDate = "$day-${month}-${year%100}"
                    size = calendarView.size.toString()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color.White)
        )
        ActiveDays(
            data = data,
            onEvent = onEvent
        )
    }
}

//@ExperimentalFoundationApi
@Composable
fun ActiveDays(
    data: List<ProgressData>,
    onEvent:(ScreenEvents)->Unit
){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        content = {
            items(data){item->
                DataView(item)
            }
        },
        modifier = Modifier
            .padding(bottom = 70.dp , end = 10.dp),
    )
}
@Composable
fun DataView(
    data : ProgressData
){
    var showDialog by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .padding(start = 10.dp , top = 10.dp)
            .clip(
                shape = RoundedCornerShape(10.dp)
            )
            .background(
                if (data.completedMinutes < 3) Color.Green
                else if(data.completedMinutes < 20) Color.Yellow
                else Color.Red
            )
            .clickable {
                showDialog = !showDialog
            }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = data.key,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
        )
    }
    if (showDialog){
        DeepView(data){
            showDialog = !showDialog
        }
    }
}

@Composable
fun DeepView(
    data : ProgressData,
    onDismiss : ()-> Unit
){
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ){
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(600.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = "Day ${data.key} Details",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White

                )
                Text(
                    text = "Productive Minutes = ${data.completedMinutes/60}",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    color = Color.White
                )
                FlatProgressBar(
                     percentage = 1f - data.progressPercentage
                )
                Row(
                    modifier = Modifier.fillMaxWidth().height(30.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    Text(text = "Completed Task", color = Color.White)
                    Text(text = "Incomplete Task" , color = Color.White)
                }
                Row (
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                    ) {
                        items(data.todoList) { lis->
                            if(lis.isCompleted){
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp )
                                        .padding(end = 15.dp)
                                        .clip(RoundedCornerShape(15.dp))
                                        .background(
                                            color = when(lis.priority){
                                                TaskPriority.GENERAL -> Color.Green
                                                TaskPriority.MEDIUM -> Color.Yellow
                                                TaskPriority.IMPORTANT -> Color.Red
                                            }
                                        )
                                        .align(Alignment.Top),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = lis.title,
                                        color = Color.Black,
                                        modifier= Modifier.padding(3.dp) ,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(data.todoList) {lis->
                            if (!lis.isCompleted){
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp)
                                        .clip(RoundedCornerShape(15.dp))
                                        .background(
                                            color = when(lis.priority){
                                                TaskPriority.GENERAL -> Color.Green
                                                TaskPriority.MEDIUM -> Color.Yellow
                                                TaskPriority.IMPORTANT -> Color.Red
                                            }
                                        )
                                        .align(Alignment.Top),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(
                                        text = lis.title,
                                        color = Color.Black,
                                        modifier= Modifier.padding(3.dp) ,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun FlatProgressBar(
    modifier: Modifier = Modifier,
    percentage:Float = 0.5f,
){
    LinearProgressIndicator(
        progress = percentage,
        modifier = modifier
            .fillMaxWidth()
            .height(30.dp)
            .clip(RoundedCornerShape(10.dp))
            .padding(4.dp),
        color = Color.Green,
        trackColor = Color.Gray
    )
}