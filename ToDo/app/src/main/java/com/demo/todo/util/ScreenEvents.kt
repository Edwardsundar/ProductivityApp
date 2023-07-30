package com.demo.todo.util

import com.demo.todo.resorce.TaskPriority

sealed interface ScreenEvents{
    object TimerStart : ScreenEvents
    object TimerStop : ScreenEvents
    object TimerRestart : ScreenEvents
    data class NewToDoList(
        val title : String ,
        val priority: TaskPriority,
        val isCompleted:Boolean
    ):ScreenEvents
}
