package com.demo.todo.util

import com.demo.todo.resorce.TaskPriority
import com.demo.todo.roomDataBase.ToDoList

sealed interface ScreenEvents{
    object TimerStart : ScreenEvents
    object TimerStop : ScreenEvents
    object TimerRestart : ScreenEvents
    data class NewToDoList(val toDoList: ToDoList):ScreenEvents

    data class CheckBoxChange(val index : Int , val isCheck : Boolean) : ScreenEvents
    data class DeleteTodo (val index: Int) : ScreenEvents
}
