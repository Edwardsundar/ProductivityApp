package com.demo.todo.state

import com.demo.todo.roomDataBase.ToDoList

data class ScreenState(
    val timerCurrentValue: Long = 1500,
    val timerOverAllValue: Long = 1500,
    val timerIsOn: Boolean = false,
    val stringTime : String = "Start!\uD83D\uDE42",
    val progressPercentage : Float = 1f,
    val todoList: MutableList<ToDoList> = mutableListOf()
)
