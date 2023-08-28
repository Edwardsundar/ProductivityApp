package com.demo.todo.ui.theme.ToDoListView

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.demo.todo.R
import com.demo.todo.navcontroller.NavRoot
import com.demo.todo.resorce.TaskPriority
import com.demo.todo.roomDataBase.ToDoList
import com.demo.todo.state.ScreenState
import com.demo.todo.ui.theme.Beige1
import com.demo.todo.ui.theme.Beige3
import com.demo.todo.ui.theme.OrangeYellow1
import com.demo.todo.ui.theme.OrangeYellow3
import com.demo.todo.ui.theme.ToDoBackGround
import com.demo.todo.util.ScreenEvents

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ToDoListView(
    navController: NavController,
    state: ScreenState,
    onEvent: (ScreenEvents)->Unit
){
    var isSelected by remember {
        mutableStateOf(false)
    }
    var columnColor by remember {
        mutableStateOf(Color.Transparent)
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    if (showDialog){
        AddNewToDo(onEvent = onEvent){selectedTitle , time , selectedPriority ->
            if (selectedTitle.length > 1) {
                val todo = ToDoList(
                    title = selectedTitle,
                    priority = selectedPriority,
                    isCompleted = false
                )
                onEvent(ScreenEvents.NewToDoList(todo))
            }
            showDialog = false
        }
    }
    //val lazyState = rememberLazyListState(10)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ToDoBackGround)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(40.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        )
        {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .width(100.dp)
                    .height(40.dp)
                    // .align(Alignment.End)
                    .background(color = OrangeYellow3)
                    .clickable {
                        isSelected = true
                        showDialog = true
                        isSelected = false
                    },
                contentAlignment = Alignment.Center
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "addToDo",
                        tint = if (isSelected) {
                            Beige1
                        } else Color.White,
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Text(
                        text = "Add New",
                        color = Color.White
                    )
                }
            }
        }
        LazyColumn(
            //state = lazyState,
            modifier = Modifier
                .padding(bottom = 80.dp)
                .background(columnColor)
        ){
            /*itemsIndexed(
                items = state.todoList,
                key = {_,items->
                    items.hashCode()
                }
            ){index,item->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart){
                            onEvent(ScreenEvents.DeleteTodo(index))
                            true
                        }
                        else
                            false
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        val color = when(dismissState.dismissDirection){
                            DismissDirection.StartToEnd -> Color.Gray
                           // DismissDirection.EndToStart -> Color.Blue
                            else -> Color.Magenta
                        }
                        Box(
                            modifier = Modifier
                                .background(color)
                                .padding(16.dp)
                        ){
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.align(Alignment.CenterEnd)
                            )
                        }
                    },
                    dismissContent = {
                        SingleListView(
                            toDoList = state.todoList[index],
                            modifier = Modifier.padding(16.dp),

                            ){isCheck , isDelete->
                            onEvent(ScreenEvents.CheckBoxChange(index = index ,isCheck = isCheck))
                        }
                    }
                )
            }*/
            items(state.todoList.size){index->
                SingleListView(
                    toDoList = state.todoList[index],
                    modifier = Modifier.padding(16.dp),

                    ){isCheck , isDelete->
                    onEvent(ScreenEvents.CheckBoxChange(index = index ,isCheck = isCheck)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SingleListView(
    toDoList: ToDoList,
    modifier: Modifier=Modifier,
    isCompleted : (Boolean , Boolean)-> Unit,

){
    var isChecked by remember {
        mutableStateOf(toDoList.isCompleted)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .height(50.dp)
            .background(
                when(toDoList.priority){
                    TaskPriority.GENERAL -> Color.Green
                    TaskPriority.MEDIUM -> Color.Yellow
                    TaskPriority.IMPORTANT -> Color.Red
                }
            )
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(30.dp)
            )
            .clickable {

            }
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp,end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = toDoList.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    toDoList.isCompleted = it
                    isChecked = it
                    isCompleted(it , false)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Cyan,
                    uncheckedColor = Color.Blue
                )
            )
        }
    }
}

@Composable
fun ToDoBox(
    title:String,
    isCompleted:Boolean
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(top = 7.dp)
    ) {

    }
}

@Composable
fun SquareToDo(

){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Black)
    ){
        Row {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(20.dp)
                    .background(Color.Green)
            )
        }
    }
}