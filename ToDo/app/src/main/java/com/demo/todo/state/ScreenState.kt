package com.demo.todo.state

import com.demo.todo.roomDataBase.ProgressData
import com.demo.todo.roomDataBase.ToDoList

data class ScreenState(
    val timerCurrentValue : Long = 1500,
    val timerOverAllValue : Long = 1500,
    val timerIsOn : Boolean = false,
    val todoList : MutableList<ToDoList> = mutableListOf()
)
