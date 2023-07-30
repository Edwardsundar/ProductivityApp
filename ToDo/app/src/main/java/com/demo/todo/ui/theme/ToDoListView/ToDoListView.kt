package com.demo.todo.ui.theme.ToDoListView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.demo.todo.R
import com.demo.todo.navcontroller.NavRoot
import com.demo.todo.ui.theme.Beige1
import com.demo.todo.ui.theme.Beige3
import com.demo.todo.ui.theme.OrangeYellow1
import com.demo.todo.ui.theme.OrangeYellow3
import com.demo.todo.ui.theme.ToDoBackGround

@Composable
fun ToDoListView(
    navController: NavController
){
    var isSelected by remember {
        mutableStateOf(false)
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    if (showDialog){
        AddNewToDo(){
            showDialog = false
        }
    }
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
                    .width(100.dp)
                    .height(40.dp)
                    // .align(Alignment.End)
                    .background(color = OrangeYellow3, shape = RoundedCornerShape(20.dp))
                    .clickable {
                        isSelected = true
                        showDialog = true
                        isSelected = false
                    }
                    ,
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
            modifier = Modifier
                .fillMaxSize()
        ){

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
