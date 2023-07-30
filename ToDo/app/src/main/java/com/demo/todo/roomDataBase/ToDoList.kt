package com.demo.todo.roomDataBase

import com.demo.todo.resorce.TaskPriority
import com.google.gson.annotations.SerializedName


data class ToDoList(
    @SerializedName("title")
    val title: String ,
    @SerializedName("priority")
    val priority : TaskPriority ,
    @SerializedName("isCompleted")
    val isCompleted : Boolean
)
