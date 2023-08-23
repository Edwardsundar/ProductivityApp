package com.demo.todo.ui.theme.ToDoListView

import android.renderscript.RenderScript.Priority
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.todo.R
import com.demo.todo.resorce.Resorce
import com.demo.todo.resorce.Resorce.Companion.priority
import com.demo.todo.resorce.TaskPriority
import com.demo.todo.roomDataBase.ToDoList
import com.demo.todo.util.ScreenEvents
import java.util.PriorityQueue

@Composable
fun AddNewToDo(
    onEvent: (ScreenEvents)->Unit ,
    modifier :Modifier = Modifier,
    dialogEvent : ()-> Unit
){

    var selectedTitle by remember {
        mutableStateOf("")
    }

    var selectedMinutes by remember {
        mutableIntStateOf(1500)
    }
    var selectedPriority by remember {
        mutableStateOf(TaskPriority.GENERAL)
    }
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            if (selectedTitle.length > 1) {
                 val todo = ToDoList(
                    title = selectedTitle,
                    priority = selectedPriority,
                    isCompleted = false
                )
                onEvent(ScreenEvents.NewToDoList(todo))
            }
            dialogEvent()
        },
        title = { Text(text = "Addd Activity") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TextField(
                    value = selectedTitle,
                    onValueChange = {
                        selectedTitle = it
                    },
                    placeholder = {
                        Text(text = "Title")
                    }
                )
                TextField(
                    value = selectedMinutes.toString(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                                    selectedMinutes = it.toInt()
                    },
                    placeholder = {
                        Text(text = "Minutes")
                    },
                    isError = selectedMinutes < 1000 || selectedMinutes > 210000
                )
                Text(
                    text = "Priority",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp ,
                    color = when(selectedPriority){
                        TaskPriority.GENERAL -> Color.Green
                        TaskPriority.MEDIUM -> Color.Yellow
                        TaskPriority.IMPORTANT -> Color.Red
                    }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    priority.forEachIndexed { index, pair ->
                        Column{
                            Text(
                                text = pair.first,
                                fontSize = 12.sp
                            )
                            Icon(
                                painter = painterResource(R.drawable.ic_circle),
                                tint = pair.second,
                                contentDescription = pair.first,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .size(35.dp)
                                    .clickable {
                                        selectedPriority = when(index){
                                            0 -> TaskPriority.GENERAL
                                            1 -> TaskPriority.MEDIUM
                                            else -> TaskPriority.IMPORTANT
                                        }
                                    }
                            )
                        }
                    }
                }
            }
        },
        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ){
                Button(
                    onClick = {
                        dialogEvent()
                    }
                ) {
                    Text(text = "Saved")
                }
            }
        }
    )
}