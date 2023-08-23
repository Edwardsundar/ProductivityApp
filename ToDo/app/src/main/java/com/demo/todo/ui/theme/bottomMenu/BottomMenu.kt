package com.demo.todo.ui.theme.bottomMenu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.demo.todo.navcontroller.NavRoot
import com.demo.todo.ui.theme.AquaBlue
import com.demo.todo.ui.theme.ButtonBlue
import com.demo.todo.ui.theme.CalenderBackGround
import com.demo.todo.ui.theme.DeepBlue
import com.demo.todo.ui.theme.ToDoBackGround


@Composable
fun CreateBottomMenu(
    bottomMenuItem: List<BottomMenuItem>,
    modifier: Modifier = Modifier,
    navController: NavController,
    bottomView: BottomView
){
    var isSelectedMenu by remember {
        mutableStateOf(bottomView)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                when(isSelectedMenu){
                    BottomView.Calender -> CalenderBackGround
                    BottomView.Home -> DeepBlue
                    BottomView.ToDo -> ToDoBackGround
                }
            )
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    )
    {
        bottomMenuItem.forEach {  item ->
            BottomMenu(
                bottomMenuItem = item,
                isSelected = isSelectedMenu == item.bottomView
            ) {
                isSelectedMenu = item.bottomView
                when (isSelectedMenu){
                    BottomView.Calender -> navController.navigate(NavRoot.CalendarViewNav.root)
                    BottomView.Home -> navController.navigate(NavRoot.HomeViewNav.root)
                    BottomView.ToDo -> navController.navigate(NavRoot.ToDoViewNav.root)
                }
            }
        }
    }
}

@Composable
fun BottomMenu(
    bottomMenuItem: BottomMenuItem,
    isSelected : Boolean = false,
    activeHighlightColor : Color = ButtonBlue,
    activeTextColor : Color = Color.White,
    inactiveTextColor : Color = AquaBlue,
    onItemTouch : ()->Unit,
   // navController: NavController
){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (isSelected) activeHighlightColor else Color.Transparent
                )
                .clickable {
                    onItemTouch()
                }
                .size(45.dp)
                .padding(10.dp)
        ) {
            Icon(
                painter = bottomMenuItem.icon,
                contentDescription = bottomMenuItem.title,
                tint = if (isSelected) {
                    activeTextColor
                } else inactiveTextColor,
                modifier = Modifier
                    .size(30.dp)
            )
        }
        Text(
            text = bottomMenuItem.title,
            color = if (isSelected) {activeTextColor} else inactiveTextColor
            )
    }

}