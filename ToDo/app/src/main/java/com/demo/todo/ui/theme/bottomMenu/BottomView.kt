package com.demo.todo.ui.theme.bottomMenu

sealed class BottomView{
    object Home : BottomView()
    object Calender : BottomView()
    object ToDo : BottomView()
}
