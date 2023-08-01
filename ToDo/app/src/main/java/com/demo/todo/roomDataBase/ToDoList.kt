package com.demo.todo.roomDataBase

import com.demo.todo.resorce.TaskPriority
import com.google.gson.annotations.SerializedName


data class ToDoList(
    @SerializedName("title")
    val title: String = "Minimum Productivity",
    @SerializedName("priority")
    val priority : TaskPriority = TaskPriority.GENERAL,
    @SerializedName("isCompleted")
    val isCompleted : Boolean = false
)
