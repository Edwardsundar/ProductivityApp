package com.demo.todo.state

import com.demo.todo.roomDataBase.ProgressData

data class ScreenState(
    val timerCurrentValue : Long ,
    val timerOverAllValue : Long ,
    val todayToDoList : ProgressData
)
